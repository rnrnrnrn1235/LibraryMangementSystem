package com.authservice.authservice.authService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.authservice.authModel.UserInfo;
import com.authservice.authservice.authRepository.AuthRepository;
import com.authservice.authservice.authRequests.AuthRequest;

@Service
public class UserInfoService implements UserDetailsService {

    // final is used to make sure these dependencies are not changed after
    // initialization
    // once per instance of the service per application lifecycle (singleton scope
    // is the default in Spring)
    // meaning the same instance is used throughout the application lifecycle
    private final AuthRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserInfoService(AuthRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // passes entity to UserInfoDetails constructor to adapt it to UserDetails
    // interface
    // then returns UserDetails object
    // used by authenticationManager if userdetails doesn't exist
    // it will throw either classCastException or no userDetails configured
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userinfo = userRepository.findByUsername(username);
        if (userinfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserInfo user = userinfo.get();

        return new UserInfoDetails(user);
    }

    public HttpStatus registerUser(AuthRequest authRequest) {
        // Check if username already exists
        Optional<UserInfo> existingUser = userRepository.findByUsername(authRequest.getUsername());
        if (existingUser.isPresent()) {
            return HttpStatus.CONFLICT;
        }
        UserInfo userinfo = new UserInfo();
        userinfo.setUsername(authRequest.getUsername());
        userinfo.setPassword(authRequest.getPassword());
        // DEFAULT assigned role is user, rest of roles is set by admins
        userinfo.setRole("ROLE_USER");
        // Ensure id is null so Mongo will generate one
        userinfo.setId(null);
        // Encode the password before saving
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        userRepository.save(userinfo);
        return HttpStatus.CREATED;

    }

    


    
    public void updateUserRole(String id, java.util.List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be empty");
        }
       







        
        Optional<UserInfo> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            UserInfo user = userOpt.get(); 
            String roleStr = String.join(",", roles);
            user.setRole(roleStr);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
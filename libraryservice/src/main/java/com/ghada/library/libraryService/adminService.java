package com.ghada.library.libraryService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ghada.library.libraryModel.User;
import com.ghada.library.libraryRepository.UserRepository;

@Service
public class adminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Value("${auth.service.url}")
    private String authServiceUrl;

    // the static list of allowed users.

    private static final Set<String> ALLOWED_ROLES = Set.of(
            "ROLE_USER",
            "ROLE_ADMIN",
            "ROLE_LIBRARIAN");

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateRoles(String id, List<String> roles) {

        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles list cannot be empty");
        }
        for (String role : roles) {
            if (!ALLOWED_ROLES.contains(role)) {
                throw new IllegalArgumentException("Invalid role: " + role);
            }
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(roles);

        // Sync with Auth Service
        try {
            restTemplate.put(authServiceUrl + "/authentication/users/" + id + "/roles", roles);
        } catch (Exception e) {
            // Log error but probably allow local update, or throw?
            // For now, logging to console is safe.
            System.err.println("Failed to sync roles with Auth Service: " + e.getMessage());
        }

        return userRepository.save(user);
    }

    public User updateName(String id, String userName) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(userName);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> searchByUsername(String name) {
        return userRepository.findByUsernameIgnoreCase(name);
    }

    public long countTotalUsers(String id) {
        return userRepository.count();
    }
}

package com.ghada.library.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghada.library.libraryModel.User;
import com.ghada.library.libraryRepository.UserRepository;

@Service
public class SyncService {

    @Autowired
    UserRepository userRepository;
     //To be fixed to findById()
     public void syncUserIfNotExists(String username, List<String> roles, String id) {
       // userRepository.findByUsername(username)
        userRepository.findById(id)
            .orElseGet(() -> {
                User u = new User();
                u.setUsername(username);
                u.setRole(roles);
                u.setId(id);
                return userRepository.save(u);
            }); 
        }
          //for getting and syncing user for elevated operations
    public User getOrSyncUser(String username, List<String> roles, String id) {
        syncUserIfNotExists(username, roles, id);
        return userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found"));
    }
    
}

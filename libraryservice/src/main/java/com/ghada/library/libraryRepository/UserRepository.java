

package com.ghada.library.libraryRepository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ghada.library.libraryModel.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(String username); //use username to find user details
    List<User> findByUsernameIgnoreCase(String username);
    long count();
} 
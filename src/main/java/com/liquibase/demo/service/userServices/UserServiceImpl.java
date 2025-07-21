package com.liquibase.demo.service.userServices;

import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUser(String userName, String password) {
        return null;
    }

    @Override
    public User createUser(User user) {
        String username = user.getUserName();
        String email = user.getEmail();

        String existingUserName = userRepository.findByUserName(username);
        String existingEmail = userRepository.findByUserEmail(email);

        if (existingUserName != null && existingUserName.equalsIgnoreCase(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (existingEmail != null && existingEmail.equalsIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }


    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public User updateUser(User user) {
        Optional<com.liquibase.demo.model.User> existingUserOptional = userRepository.findById(user.getId());

        if (existingUserOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + user.getId());
        }
        User existingUser = existingUserOptional.get();
        existingUser.setUserName(user.getUserName());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setProfilePicUrl(user.getProfilePicUrl());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    public ResponseEntity<User> loginUser(String usernameOrEmail, String password) {
        User user = userRepository.findByUserNameOrEmailAndPassword(usernameOrEmail, password);

        if (user == null) {
            throw new RuntimeException("Invalid usernameOrEmail or password");
        }

        return ResponseEntity.ok(user);
    }


    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

}

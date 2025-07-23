package com.liquibase.demo.service.userServices;

import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;


    @Override
    public User getUser(String userName, String password) {
        return null;
    }


    @Override
    @Transactional
    public User deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User ID not found");
        }

        User user = optionalUser.get();

        if (user.getDeletedAt() != null) {
            throw new UserNotFoundException("User already deleted");
        }

        user.setDeletedAt(LocalDateTime.now());
        return userRepository.save(user);
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
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

}

package com.liquibase.demo.service.authService;

import com.liquibase.demo.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public User createUser(User user);

    public ResponseEntity<User> loginUser(String username, String password);


}

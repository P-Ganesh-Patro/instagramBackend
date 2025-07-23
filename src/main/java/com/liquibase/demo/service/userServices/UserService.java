package com.liquibase.demo.service.userServices;

import com.liquibase.demo.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public User getUser(String username, String password);


    public User deleteUser(Long id);

    public User updateUser(User user);

    User getUserById(Long id);


}

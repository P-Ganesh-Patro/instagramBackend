package com.liquibase.demo.controller;


import com.liquibase.demo.model.User;
import com.liquibase.demo.response.ResponseHandler;
import com.liquibase.demo.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServiceImpl userImpl;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        try {
            return ResponseHandler.responseHandler("User created successfully", HttpStatus.CREATED, userImpl.createUser(user));
        } catch (RuntimeException e) {
            return ResponseHandler.responseHandler(e.getMessage(), HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            return ResponseHandler.responseHandler("User creation failed: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            return ResponseHandler.responseHandler("User updated successfully", HttpStatus.OK, userImpl.updateUser(user));
        } catch (RuntimeException e) {
            return ResponseHandler.responseHandler("User update failed: " + e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.responseHandler("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            return ResponseHandler.responseHandler("user deleted successfully...", HttpStatus.OK, userImpl.deleteUser(id));
        } catch (RuntimeException e) {
            return ResponseHandler.responseHandler("user not found" + e.getMessage(), HttpStatus.NOT_FOUND, null);

        } catch (Exception e) {
            return ResponseHandler.responseHandler("error deleted user.." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String, String> loginData) {
        try {
            String usernameOrEmail = loginData.get("username");
//            or email
            String password = loginData.get("password");

            return ResponseHandler.responseHandler("Login successful", HttpStatus.OK, userImpl.loginUser(usernameOrEmail, password));
        } catch (RuntimeException e) {
            return ResponseHandler.responseHandler(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
        } catch (Exception e) {
            return ResponseHandler.responseHandler("Login failed: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


}

package com.liquibase.demo.controller;

import com.liquibase.demo.dto.LoginDTO;
import com.liquibase.demo.dto.SignUpDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.jwt.JwtAuthFilter;
import com.liquibase.demo.jwt.JwtUtil;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.response.ResponseHandler;
import com.liquibase.demo.service.authService.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth/user")
@AllArgsConstructor
@Tag(name = "User Authentication", description = "Handles user signup and login operations")
public class AuthController {

    private final AuthServiceImpl authService;

    private final JwtUtil jwtUtil;

    @Operation(summary = "user signUp")
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<SignUpDTO>> signUpUser(@RequestBody User user) {
        try {

            LocalDate localDateTime = LocalDate.now();
            if ((user.getDOB().isAfter(localDateTime))) {
                throw new IllegalStateException("Date of birth must be before the current date");
            }
            User createdUser = authService.createUser(user);
            SignUpDTO dto = new SignUpDTO(
                    createdUser.getId(),
                    createdUser.getUserName(),
                    createdUser.getFirstName(),
                    createdUser.getLastName(),
                    createdUser.getEmail(),
                    createdUser.getDOB()
            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User registered successfully", HttpStatus.CREATED, dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            APIResponse<SignUpDTO> response = new APIResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "user Login")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginDTO>> loginUser(@RequestBody Map<String, String> loginData) {
        try {
            String usernameOrEmail = loginData.get("username");
//            or email
            String password = loginData.get("password");
            User user = authService.loginUser(usernameOrEmail, password).getBody();

            LoginDTO loginDTO = new LoginDTO(
                    user.getId(),
                    user.getUserName(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getComments(),
                    user.getPosts(),
                    user.getReactions()
            );

            String accessToken = jwtUtil.generateAccessToken(user.getUserName());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUserName());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", loginDTO);
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToke", refreshToken);

            APIResponse<Map<String, Object>> response = new APIResponse<>(
                    "Login successful",
                    HttpStatus.OK,
                    responseData
            );
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Invalid username or password");
        } catch (Exception e) {
            throw new com.liquibase.demo.exception.Exception("Login failed:- " + e.getMessage());
        }
    }

}

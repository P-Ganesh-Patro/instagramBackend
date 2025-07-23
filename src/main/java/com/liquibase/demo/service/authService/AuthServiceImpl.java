package com.liquibase.demo.service.authService;

import com.liquibase.demo.dto.LoginDTO;
import com.liquibase.demo.dto.SignUpDTO;
import com.liquibase.demo.exception.Exception;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.AuthRepository;
import com.liquibase.demo.response.APIResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;


    @Override
    public User createUser(User user) {
        String username = user.getUserName();
        String email = user.getEmail();


        String existingUserName = authRepository.findByUserName(username);
        String existingEmail = authRepository.findByUserEmail(email);

        if (existingUserName != null && existingUserName.equalsIgnoreCase(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (existingEmail != null && existingEmail.equalsIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }

        return authRepository.save(user);
    }

    @Override
    public ResponseEntity<User> loginUser(String usernameOrEmail, String password) {


        User user = authRepository.findByUserNameOrEmailAndPassword(usernameOrEmail, password);
        if (user == null) {
            throw new RuntimeException("Invalid usernameOrEmail or password");
        }
        if (user.getDeletedAt() != null) {
            throw new UserNotFoundException("user not found, user deleted..");
        }

        return ResponseEntity.ok(user);
    }


}

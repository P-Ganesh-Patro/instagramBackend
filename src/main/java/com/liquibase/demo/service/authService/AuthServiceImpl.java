package com.liquibase.demo.service.authService;


import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
//    private final JwtUtil jwtUtil;


    @Autowired
    JavaMailSender javaMailSender;


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

        String emailBody =
                "Hey, " + user.getFirstName() +
                        """
                                            "Just wanted to let you know I've created a new Instagram account, +
                                            "Looking forward to connecting with you there!+
                                            "Best"
                                """;


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("New Instagram Account Created!");
        simpleMailMessage.setText(emailBody);
        javaMailSender.send(simpleMailMessage);
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

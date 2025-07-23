package com.liquibase.demo.controller;


import com.liquibase.demo.dto.SignUpDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.response.ResponseHandler;
import com.liquibase.demo.service.userServices.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserServiceImpl userServiceImpl;

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userServiceImpl.updateUser(user);

            SignUpDTO dto = new SignUpDTO(
                    updatedUser.getId(),
                    updatedUser.getUserName(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getEmail()
            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User updated successfully", HttpStatus.OK, dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("User update failed: " + e.getMessage(), HttpStatus.NOT_FOUND, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> deleteUser(@PathVariable Long id) {
        try {
            User deletedUser = userServiceImpl.deleteUser(id);

            SignUpDTO dto = new SignUpDTO(
                    deletedUser.getId(),
                    deletedUser.getUserName(),
                    deletedUser.getFirstName(),
                    deletedUser.getLastName(),
                    deletedUser.getEmail()
            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User deleted successfully", HttpStatus.OK, dto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("User deletion failed: " + e.getMessage(), HttpStatus.NOT_FOUND, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> userGetById(@PathVariable Long id) {
        try {
            User user = userServiceImpl.getUserById(id);
            SignUpDTO signUpDTO = new SignUpDTO(
                    user.getId(),
                    user.getUserName(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
            );
            APIResponse<SignUpDTO> response = new APIResponse<>(
                    "user fetched successfully",
                    HttpStatus.OK,
                    signUpDTO
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }

    }


}

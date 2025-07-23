package com.liquibase.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}

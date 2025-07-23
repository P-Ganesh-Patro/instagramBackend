package com.liquibase.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupPostResponseDTO {
    private Long id;
    private String groupName;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}

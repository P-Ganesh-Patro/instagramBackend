package com.liquibase.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupResponseDTO {
    private Long id;
    private String groupName;
    private String description;
    private String createdByName;
    private LocalDateTime createdAt;
}

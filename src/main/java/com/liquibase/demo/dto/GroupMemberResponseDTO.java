package com.liquibase.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMemberResponseDTO {
    private Long id;
    private String userName;
    private String groupName;
    private String roleName;
    private LocalDateTime createdAt;
}

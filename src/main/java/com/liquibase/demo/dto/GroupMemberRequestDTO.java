package com.liquibase.demo.dto;

import lombok.Data;

@Data
public class GroupMemberRequestDTO {
    private Long groupId;
    private Long userId;
    private Long roleId;
}

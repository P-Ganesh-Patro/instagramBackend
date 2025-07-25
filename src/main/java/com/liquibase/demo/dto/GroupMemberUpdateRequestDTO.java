package com.liquibase.demo.dto;


import lombok.Data;

@Data
public class GroupMemberUpdateRequestDTO {
    private Long roleId;
    private Long groupId;
}

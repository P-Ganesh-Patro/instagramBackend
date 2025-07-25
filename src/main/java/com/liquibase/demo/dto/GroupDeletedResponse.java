package com.liquibase.demo.dto;

import lombok.Data;

@Data
public class GroupDeletedResponse {
    private Long groupId;
    protected String groupName;
}

package com.liquibase.demo.dto;

import lombok.Data;

@Data
public class GroupRequestDTO {
    private Long createdById;
    private String groupName;
    private String description;
}

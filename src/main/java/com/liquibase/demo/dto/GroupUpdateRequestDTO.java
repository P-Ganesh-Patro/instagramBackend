package com.liquibase.demo.dto;

import lombok.Data;

@Data
public class GroupUpdateRequestDTO {
    private String groupName;
    private String description;
}

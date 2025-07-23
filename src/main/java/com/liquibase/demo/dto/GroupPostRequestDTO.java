package com.liquibase.demo.dto;

import lombok.Data;

@Data
public class GroupPostRequestDTO {
    private Long groupId;
    private Long userId;
    private Long postId;
    private String content;
}

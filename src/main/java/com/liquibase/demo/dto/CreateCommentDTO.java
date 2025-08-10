package com.liquibase.demo.dto;


import lombok.Data;

@Data
public class CreateCommentDTO {
    private Long userId;
    private Long postId;
    private String commentOnType;
    private String comment;
}

package com.liquibase.demo.dto;

import java.time.LocalDateTime;

public class CommentUpdateResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String commentOnType;
    private String comment;
    private LocalDateTime updatedAt;
}

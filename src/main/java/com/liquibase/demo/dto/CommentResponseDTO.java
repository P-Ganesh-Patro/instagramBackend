package com.liquibase.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String commentOnType;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDTO(Long id, Long userId, Long postId, String commentOnType, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentOnType = commentOnType;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

}

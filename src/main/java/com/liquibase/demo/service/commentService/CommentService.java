package com.liquibase.demo.service.commentService;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.model.Comment;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(Comment comment);

    List<CommentResponseDTO> getCommentsByPostId(Long postId);

    List<CommentResponseDTO> getCommentsByUserId(Long userId);

    CommentResponseDTO deleteComment(Long commentId);
}

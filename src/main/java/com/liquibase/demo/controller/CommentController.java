package com.liquibase.demo.controller;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.commentService.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class CommentController {


    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/comment")
    public ResponseEntity<APIResponse<CommentResponseDTO>> createComment(@RequestBody Comment comment) {
        CommentResponseDTO created = commentService.createComment(comment);
        return new ResponseEntity<>(
                new APIResponse<>("Comment created successfully", HttpStatus.CREATED, created),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<APIResponse<List<CommentResponseDTO>>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(
                new APIResponse<>("Comments fetched successfully", HttpStatus.OK, comments),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<CommentResponseDTO>>> getCommentsByUser(@PathVariable Long userId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByUserId(userId);
        return new ResponseEntity<>(
                new APIResponse<>("User comments fetched successfully", HttpStatus.OK, comments),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<APIResponse<CommentResponseDTO>> deleteComment(@PathVariable Long commentId) {
        CommentResponseDTO deleted = commentService.deleteComment(commentId);
        return new ResponseEntity<>(
                new APIResponse<>("Comment soft deleted", HttpStatus.OK, deleted),
                HttpStatus.OK
        );
    }

}

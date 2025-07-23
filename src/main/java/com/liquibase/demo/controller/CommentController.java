package com.liquibase.demo.controller;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.commentService.CommentService;
import com.liquibase.demo.service.commentService.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public ResponseEntity<APIResponse<CommentResponseDTO>> createComment(@RequestBody Comment comment) {
        CommentResponseDTO created = commentService.createComment(comment);
        APIResponse<CommentResponseDTO> response = new APIResponse<>(
                "Comment created successfully",
                HttpStatus.CREATED,
                created
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponse<CommentResponseDTO>> updateComment(@RequestBody Comment comment) {
        CommentResponseDTO updated = commentService.updateComment(comment);

        APIResponse<CommentResponseDTO> response = new APIResponse<>("comment update successfully", HttpStatus.OK, updated);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @GetMapping("/{postId}")
    public ResponseEntity<APIResponse<List<CommentResponseDTO>>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        APIResponse<List<CommentResponseDTO>> response = new APIResponse<>("comment fetched successfully", HttpStatus.OK, comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<APIResponse<List<CommentResponseDTO>>> getCommentsByUser(@PathVariable("userId") Long userId) {
//        List<CommentResponseDTO> comments = commentService.getCommentsByUserId(userId);
//        return new ResponseEntity<>(
//                new APIResponse<>("User comments fetched successfully", HttpStatus.OK, comments),
//                HttpStatus.OK
//        );
//    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<APIResponse<CommentResponseDTO>> deleteComment(@PathVariable Long commentId) {
        CommentResponseDTO deleted = commentService.deleteComment(commentId);
        return new ResponseEntity<>(
                new APIResponse<>("Comment deleted", HttpStatus.OK, deleted),
                HttpStatus.OK
        );
    }

}

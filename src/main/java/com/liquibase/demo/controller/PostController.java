package com.liquibase.demo.controller;


import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.exception.UnauthorizedException;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.authService.AuthServiceImpl;
import com.liquibase.demo.service.postServices.PostService;
import com.liquibase.demo.service.userServices.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.liquibase.demo.exception.Exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {


    private final PostService postService;

    private final UserService userService;

    private final AuthServiceImpl authService;


    @PostMapping()
    public ResponseEntity<APIResponse<PostsResponseDTO>> createPosts(@RequestBody Post post, @RequestParam String userNameOrEmail, @RequestParam String password) {
        try {
            User user = authService.loginUser(userNameOrEmail, password).getBody();

            if (user != null) {
//                post.setUserId(user.getId());
                post.setUser(user);
                PostsResponseDTO postSaved = postService.createPost(post);
                PostsResponseDTO dto = new PostsResponseDTO(
                        postSaved.getId(),
                        postSaved.getUserId(),
                        postSaved.getContent(),
                        postSaved.getCreatedAt(),
                        postSaved.getUpdatedAt()
                );

                APIResponse<PostsResponseDTO> response = new APIResponse<>(
                        "Post created successfully",
                        HttpStatus.CREATED,
                        dto
                );
                return new ResponseEntity<>(response, HttpStatus.CREATED);


            } else {
                throw new UserNotFoundException("user not found..");
            }
        } catch (Exception e) {
            throw new Exception("exception:- " + e.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PostsResponseDTO>> updatePosts(
            @RequestBody Post post,
            @PathVariable Long id)
    {

        if (post.getUser() != null && post.getUser().getDeletedAt() != null) {
            throw new UserNotFoundException("User is deleted with ID: " + post.getUser().getId());
        }

        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            throw new UserNotFoundException("Post not found with ID: " + id);
        }

        User existingUser = userService.getUserById(post.getUser().getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with ID: " + post.getUser().getId());
        }

        if (!existingPost.getUser().getId().equals(post.getUser().getId())) {
            throw new UnauthorizedException("Post does not belong to the provided user");
        }

        if (existingPost.getDeletedAt() != null) {
            throw new IllegalStateException("Cannot update a deleted post");
        }

        if (post.getContent() != null && !post.getContent().trim().isEmpty()) {
            existingPost.setContent(post.getContent().trim());
        }

        existingPost.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postService.updatePost(existingPost);

        PostsResponseDTO dto = new PostsResponseDTO(
                updatedPost.getId(),
                updatedPost.getUser().getId(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUpdatedAt()
        );

        APIResponse<PostsResponseDTO> response = new APIResponse<>(
                "Post updated successfully",
                HttpStatus.OK,
                dto
        );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<PostsResponseDTO>> deletePosts(@PathVariable Long id, @RequestParam("userId") Long userId) {
        try {
            Post existingPost = postService.getPostById(id);
            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }

            if (!existingPost.getUser().getId().equals(userId)) {
                throw new UnauthorizedException("Post does not belong to user");
            }

            if(existingPost.getDeletedAt() != null){
                throw new Exception("post is already deleted..");
            }

            Post deletedPost = postService.deletePost(id);

            PostsResponseDTO dto = new PostsResponseDTO(
                    deletedPost.getId(),
                    deletedPost.getUser().getId(),
                    deletedPost.getContent(),
                    deletedPost.getCreatedAt(),
                    deletedPost.getUpdatedAt()
            );

            APIResponse<PostsResponseDTO> response = new APIResponse<>(
                    "Post deleted successfully ",
                    HttpStatus.OK,
                    dto
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Post not found or already deleted with ID: " + id);
        } catch (Exception e) {
            throw new Exception("Delete Error: " + e.getMessage());
        }
    }


    @GetMapping("user/{userId}")
    public ResponseEntity<APIResponse<List<PostsResponseDTO>>> getAllPostByUserId(@PathVariable Long userId) {
        try {
            List<Post> allPosts = postService.getAllPosts(userId);
            List<PostsResponseDTO> responseDTOList = allPosts.stream()
                    .map(post -> new PostsResponseDTO(
                            post.getId(),
                            post.getUser().getId(),
                            post.getContent(),
                            post.getCreatedAt(),
                            post.getUpdatedAt()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new APIResponse<>("Fetch all posts successfully", HttpStatus.OK, responseDTOList));


        } catch (Exception e) {
            throw new Exception("all post fetch- Error:" + e.getMessage());

        }

    }

}

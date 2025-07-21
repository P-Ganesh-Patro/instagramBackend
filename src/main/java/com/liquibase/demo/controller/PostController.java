package com.liquibase.demo.controller;


import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.exception.UnauthorizedException;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.response.ResponseHandler;
import com.liquibase.demo.service.postServices.PostService;
import com.liquibase.demo.service.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.liquibase.demo.exception.Exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @PostMapping("/post")
    public ResponseEntity<APIResponse<PostsResponseDTO>> createPost(@RequestBody Post post, @RequestParam String userNameOrEmail, @RequestParam String password) {
        try {

            User user = userService.loginUser(userNameOrEmail, password).getBody();

            if (user != null) {
                post.setUserId(user.getId());
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
                return new ResponseEntity<APIResponse<PostsResponseDTO>>(response, HttpStatus.CREATED);


            } else {
                throw new UserNotFoundException("user not found..");
            }
        } catch (Exception e) {
            throw new Exception("exception:- " + e.getMessage());
        }


    }

    //normal user
    @PutMapping("/post/update/{id}")
    public ResponseEntity<APIResponse<PostsResponseDTO>> updatePost(
            @RequestBody Post post,
            @PathVariable Long id,
            @RequestParam("userId") Long userId) {
        try {
            Post existingPost = postService.getPostById(id);
            User existingUser = userService.getUserById(userId);

            if (existingPost == null) {
                throw new UserNotFoundException("Post not found with ID: " + id);
            }

            if (existingUser == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }

            if (!existingPost.getUserId().equals(userId)) {
                throw new UnauthorizedException("Post does not belong to user");
            }

            if (post.getContent() != null && !post.getContent().trim().isEmpty()) {
                existingPost.setContent(post.getContent());
            }
            if (existingPost.getDeletedAt() != null) {
                throw new IllegalStateException("can not updated deleted post..");
            }


            existingPost.setUpdatedAt(LocalDateTime.now());
            Post updatedPost = postService.updatePost(existingPost);

            PostsResponseDTO dto = new PostsResponseDTO(
                    updatedPost.getId(),
                    updatedPost.getUserId(),
                    updatedPost.getContent(),
                    updatedPost.getCreatedAt(),
                    updatedPost.getUpdatedAt()
            );

            APIResponse<PostsResponseDTO> response = new APIResponse(
                    "post updated successfully",
                    HttpStatus.OK,
                    dto
            );
            return new ResponseEntity<APIResponse<PostsResponseDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw e;

        }
    }


    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<APIResponse<PostsResponseDTO>> deletePost(@PathVariable Long id, @RequestParam("userId") Long userId) {
        try {
            Post existingPost = postService.getPostById(id);
            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }

            if (!existingPost.getUserId().equals(userId)) {
                throw new UnauthorizedException("Post does not belong to user");
            }

            Post deletedPost = postService.deletePost(id);

            PostsResponseDTO dto = new PostsResponseDTO(
                    deletedPost.getId(),
                    deletedPost.getUserId(),
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


    @GetMapping("user/posts/{userId}")
    public ResponseEntity<APIResponse<List<PostsResponseDTO>>> getAllPostByUserId(@PathVariable Long userId) {
        try {
            List<Post> allPosts = postService.getAllPosts(userId);
            List<PostsResponseDTO> responseDTOList = allPosts.stream()
                    .map(post -> new PostsResponseDTO(
                            post.getId(),
                            post.getUserId(),
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

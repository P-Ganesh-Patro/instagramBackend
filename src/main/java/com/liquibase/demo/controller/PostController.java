package com.liquibase.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquibase.demo.dto.PostAndPostMediaDTO;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.liquibase.demo.exception.Exception;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<PostAndPostMediaDTO>> createPosts(
            @RequestPart("posts") String postJson,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestParam String userNameOrEmail,
            @RequestParam String password) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Post post = mapper.readValue(postJson, Post.class);

            User user = authService.loginUser(userNameOrEmail, password).getBody();
            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            post.setUser(user);
            PostAndPostMediaDTO savedPost = postService.createPost(post, files);

            return new ResponseEntity<>(
                    new APIResponse<>("Post with media created successfully", HttpStatus.CREATED, savedPost),
                    HttpStatus.CREATED
            );
        } catch (IOException e) {
            APIResponse<PostAndPostMediaDTO> response = new APIResponse(
                    "Invalid file type",
                    HttpStatus.BAD_REQUEST,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);


        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PostAndPostMediaDTO>> updatePosts(
            @RequestPart("posts") String postContent,
            @RequestPart(value = "file", required = false) List<MultipartFile> file,
            @PathVariable Long id) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Post posts = mapper.readValue(postContent, Post.class);

        Post postChecker = postService.getPostById(id);
        if (postChecker == null || postChecker.getDeletedAt() != null) {
            throw new UserNotFoundException("post not found with id:- " + id);
        }

        posts.setId(postChecker.getId());
        posts.setUser(postChecker.getUser());
        posts.setCreatedAt(postChecker.getCreatedAt());
        posts.setUpdatedAt(LocalDateTime.now());
        User existingUser = userService.getUserById(postChecker.getUser().getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with ID: " + postChecker.getUser().getId());
        }


        PostAndPostMediaDTO updatePost = postService.updatePost(posts, file);


        PostAndPostMediaDTO dto = new PostAndPostMediaDTO(
                updatePost.getId(),
                updatePost.getUserId(),
                updatePost.getContent(),
                updatePost.getCreatedAt(),
                updatePost.getUpdatedAt(),
                updatePost.getPostMediaDTOS()
        );

        APIResponse<PostAndPostMediaDTO> response = new APIResponse<>(
                "Post updated successfully",
                HttpStatus.OK,
                dto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<PostsResponseDTO>> deletePosts(
            @PathVariable Long id,
            @RequestParam("userId") Long userId) {

        try {
            Post existingPost = postService.getPostById(id);
            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>("User not found with ID: " + userId, HttpStatus.NOT_FOUND, null));
            }

            if (!existingPost.getUser().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new APIResponse<>("Post does not belong to user", HttpStatus.UNAUTHORIZED, null));
            }

            if (existingPost.getDeletedAt() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>("Post is already deleted", HttpStatus.BAD_REQUEST, null));
            }

            Post deletedPost = postService.deletePost(id);

            PostsResponseDTO dto = new PostsResponseDTO(
                    deletedPost.getId(),
                    deletedPost.getUser().getId(),
                    deletedPost.getContent(),
                    deletedPost.getCreatedAt(),
                    deletedPost.getUpdatedAt()
            );

            return ResponseEntity.ok(new APIResponse<>("Post deleted successfully", HttpStatus.OK, dto));

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>("Post not found with ID: " + id, HttpStatus.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>("Delete Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<Post>>> getAllPostByUserId(@PathVariable Long userId) {
        try {
            List<Post> allPosts = postService.getAllPosts(userId);

            // Optionally filter out soft-deleted posts
            List<Post> activePosts = allPosts.stream()
                    .filter(post -> post.getDeletedAt() == null)
                    .toList();

            return ResponseEntity.ok(new APIResponse<>("Fetch all posts successfully", HttpStatus.OK, activePosts));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>("All post fetch - Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }
}

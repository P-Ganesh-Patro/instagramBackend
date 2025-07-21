package com.liquibase.demo.service.postServices;

import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(Long id);

    PostsResponseDTO createPost(Post post);

    Post updatePost(Post post);

    Post deletePost(Long id);

    Post getPostById(Long id);


}

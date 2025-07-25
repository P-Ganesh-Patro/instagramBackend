package com.liquibase.demo.service.postServices;

import com.liquibase.demo.dto.PostAndPostMediaDTO;
import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts(Long id);

    PostAndPostMediaDTO createPost(Post post, List<MultipartFile> files) throws IOException;

    PostAndPostMediaDTO updatePost(Post post, List<MultipartFile> files);

    Post deletePost(Long id);

    Post getPostById(Long id);


}

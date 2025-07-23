package com.liquibase.demo.service.postServices;

import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public List<Post> getAllPosts(Long id) {
        List<Post> allPosts = postRepository.findByUserId(id);
        return allPosts.stream().filter(posts -> posts.getDeletedAt() == null).collect(Collectors.toList());
    }

    @Override
    public PostsResponseDTO createPost(Post post) {
        Post savedPost = postRepository.save(post);

        return new PostsResponseDTO(
                savedPost.getId(),
                savedPost.getUser().getId(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );

    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post postToDelete = postOptional.get();
            postToDelete.setDeletedAt(LocalDateTime.now());
            return postRepository.save(postToDelete);
        } else {
            throw new NoSuchElementException("Post not found with id: " + id);
        }
    }


    @Override
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }


}

package com.liquibase.demo.service.postServices;

import com.liquibase.demo.dto.PostAndPostMediaDTO;
import com.liquibase.demo.dto.PostMediaDTO;
import com.liquibase.demo.dto.PostsResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.PostMedia;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CloudinaryService cloudinaryService;


    @Override
    public List<Post> getAllPosts(Long id) {
        List<Post> allPosts = postRepository.findByUserId(id);
        return allPosts.stream().filter(posts -> posts.getDeletedAt() == null).collect(Collectors.toList());
    }

    @Override
    public PostAndPostMediaDTO createPost(Post post, List<MultipartFile> files) throws IOException {

        List<PostMedia> postMediaList = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String url = cloudinaryService.upload(file);
                String mediaType = file.getContentType();

                PostMedia postMedia = new PostMedia();
                postMedia.setMediaUrl(url);
                postMedia.setPost(post);
                postMedia.setMediaType(mediaType);

                postMediaList.add(postMedia);

            }
        }
        post.setMedia(postMediaList);

        Post savePost = postRepository.save(post);

        List<PostMediaDTO> mediaDTOList = savePost.getMedia().stream()
                .map(media -> new PostMediaDTO(media.getMediaUrl(), media.getMediaType()))
                .toList();

        return new PostAndPostMediaDTO(
                savePost.getId(),
                savePost.getUser().getId(),
                savePost.getContent(),
                savePost.getCreatedAt(),
                savePost.getUpdatedAt(),
                mediaDTOList
        );
    }

    @Override
    public PostAndPostMediaDTO updatePost(Post post, List<MultipartFile> files) {
        try {
            List<PostMedia> postMediaList = new ArrayList<>();
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String url = cloudinaryService.upload(file);
                    String mediaType = file.getContentType();

                    PostMedia postMedia = new PostMedia();
                    postMedia.setMediaUrl(url);
                    postMedia.setPost(post);
                    postMedia.setMediaType(mediaType);
                    postMedia.setUpdatedAt(LocalDateTime.now());

                    postMediaList.add(postMedia);

                }
                post.setMedia(postMediaList);

            }
            Post savedPost = postRepository.save(post);

            List<PostMediaDTO> mediaDTOList = savedPost.getMedia().stream()
                    .map(media -> new PostMediaDTO(media.getMediaUrl(), media.getMediaType()))
                    .toList();

            PostAndPostMediaDTO postAndPostMediaDTO = new PostAndPostMediaDTO(
                    savedPost.getId(),
                    savedPost.getUser().getId(),
                    savedPost.getContent(),
                    savedPost.getCreatedAt(),
                    savedPost.getUpdatedAt(),
                    mediaDTOList
            );
            return postAndPostMediaDTO;

        } catch (IOException e) {
            throw new RuntimeException("error while update:- " + e.getMessage());
        }
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

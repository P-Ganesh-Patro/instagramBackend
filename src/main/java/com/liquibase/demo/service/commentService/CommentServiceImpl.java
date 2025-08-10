package com.liquibase.demo.service.commentService;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.dto.CreateCommentDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.CommentRepository;
import com.liquibase.demo.repository.PostRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentResponseDTO createComment(CreateCommentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentOnType(dto.getCommentOnType());
        comment.setComment(dto.getComment());
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return toDTO(saved);
    }

    @Override
    public CommentResponseDTO updateComment(  Long commentId, CreateCommentDTO dto) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUser(user);
        }

        if (dto.getPostId() != null) {
            Post post = postRepository.findById(dto.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            existing.setPost(post);
        }

        existing.setCommentOnType(dto.getCommentOnType());
        existing.setComment(dto.getComment());
        existing.setUpdatedAt(LocalDateTime.now());

        Comment updated = commentRepository.save(existing);

        return toDTO(updated);
    }

    @Override
    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

//    @Override
//    public List<CommentResponseDTO> getCommentsByUserId(Long userId) {
//        return commentRepository.findByUserId(userId)
//                .stream().map(this::toDTO).collect(Collectors.toList());
//    }

    @Override
    public CommentResponseDTO deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        comment.setDeletedAt(LocalDateTime.now());
        Comment deleted = commentRepository.save(comment);
        return toDTO(deleted);
    }


    private CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getCommentOnType(),
                comment.getComment()
        );
    }

}

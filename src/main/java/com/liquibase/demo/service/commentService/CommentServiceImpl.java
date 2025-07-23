package com.liquibase.demo.service.commentService;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentResponseDTO createComment(Comment comment) {

//        Post post = comment.getPost();
//        System.out.println(comment.getPost().getCreatedAt() + " found");
//        System.out.println(comment.getPost().getId() + " post id");
//        System.out.println(comment.getUser().getId() + " user id");
//
//        if (post.getDeletedAt() != null) {
//            System.out.println("no date");
//            throw new UserNotFoundException("post not available");
//        }
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);

        return toDTO(saved);
    }

    @Override
    public CommentResponseDTO updateComment(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        Comment update = commentRepository.save(comment);
        return toDTO(update);
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

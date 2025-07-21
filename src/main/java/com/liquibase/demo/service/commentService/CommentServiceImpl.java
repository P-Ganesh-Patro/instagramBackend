package com.liquibase.demo.service.commentService;

import com.liquibase.demo.dto.CommentResponseDTO;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liquibase.demo.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
//package com.liquibase.demo.service.reactionService;
//
//import com.liquibase.demo.dto.ReactionDTO;
//import com.liquibase.demo.model.Reaction;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ReactionService {
//    Reaction saveReaction(ReactionDTO dto);
//
//    List<Reaction> getAllReactions();
//
//    Optional<Reaction> getReactionById(Long id);
//
//    Reaction updateReaction(Long id, ReactionDTO dto);
//
//    void softDeleteReaction(Long id);
//}

@Service
public class CommentServiceImpl implements CommentService{


    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentResponseDTO createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);

        return toDTO(saved);
    }

    @Override
    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponseDTO> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

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
                comment.getUserId(),
                comment.getPostId(),
                comment.getCommentOnType(),
                comment.getComment(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

}

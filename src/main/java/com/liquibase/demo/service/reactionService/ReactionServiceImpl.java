package com.liquibase.demo.service.reactionService;

import com.liquibase.demo.dto.ReactionDTO;
import com.liquibase.demo.dto.ReactionResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.model.Reaction;
import com.liquibase.demo.model.ReactionType;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.CommentRepository;
import com.liquibase.demo.repository.ReactionRepository;
import com.liquibase.demo.repository.ReactionTypeRepository;
import com.liquibase.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReactionTypeRepository reactionTypeRepository;


    @Override
    public ReactionResponseDTO createReaction(ReactionDTO reactionDTO) {
        User user = userRepository.findById(reactionDTO.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        Comment comment = commentRepository.findById(reactionDTO.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        ReactionType reactionType = reactionTypeRepository.findById(reactionDTO.getReactionTypeId())
                .orElseThrow(() -> new RuntimeException("invalid reaction Id"));

        Reaction reaction = new Reaction();
        reaction.setUser(user);
        reaction.setComment(comment);
        reaction.setReactionType(reactionType);
        reaction.setReactedType(reactionDTO.getReactedType());
        reaction.setCreatedAt(LocalDateTime.now());
        Reaction saved = reactionRepository.save(reaction);

        return ReactionResponseDTO.builder()
                .id(saved.getId())
                .userName(user.getUserName())
                .comment(comment.getComment())
                .reactionType(reactionType.toString())
                .reactedType(saved.getReactedType())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<ReactionResponseDTO> getReactionsByComment(Long commentId) {
        List<Reaction> reactions = reactionRepository.findByCommentId(commentId);

        return reactions.stream().map(reaction -> ReactionResponseDTO.builder()
                .id(reaction.getId())
                .userName(reaction.getUser().getUserName())
                .comment(reaction.getComment().getComment())
                .reactionType(reaction.getReactionType().toString())
                .reactedType(reaction.getReactedType())
                .createdAt(reaction.getCreatedAt())
                .build()).collect(Collectors.toList());
    }
}


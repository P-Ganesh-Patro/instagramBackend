package com.liquibase.demo.service.reactionService;

import com.liquibase.demo.dto.ReactionDTO;
import com.liquibase.demo.dto.ReactionResponseDTO;
import com.liquibase.demo.model.Reaction;

import java.util.List;
import java.util.Optional;

public interface ReactionService {
    ReactionResponseDTO createReaction(ReactionDTO reactionDTO);
    List<ReactionResponseDTO> getReactionsByComment(Long commentId);
}

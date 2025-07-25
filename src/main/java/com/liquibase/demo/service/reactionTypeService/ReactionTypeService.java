package com.liquibase.demo.service.reactionTypeService;

import com.liquibase.demo.dto.ReactionTypeRequestDTO;
import com.liquibase.demo.dto.ReactionTypeResponseDTO;

import java.util.List;

public interface ReactionTypeService {
    ReactionTypeResponseDTO createReactionType(ReactionTypeRequestDTO requestDTO);
    List<ReactionTypeResponseDTO> getAllReactionTypes();
    ReactionTypeResponseDTO getReactionTypeById(Long id);
    ReactionTypeResponseDTO updateReactionType(Long id, ReactionTypeRequestDTO requestDTO);
    void deleteReactionType(Long id);
}

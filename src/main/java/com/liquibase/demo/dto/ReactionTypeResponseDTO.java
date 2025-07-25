package com.liquibase.demo.dto;

import com.liquibase.demo.model.TypeOfReaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionTypeResponseDTO {
    private Long id;
    private TypeOfReaction reactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

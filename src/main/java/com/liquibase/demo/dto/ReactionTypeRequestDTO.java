package com.liquibase.demo.dto;

import com.liquibase.demo.model.TypeOfReaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionTypeRequestDTO {
    private TypeOfReaction reactionType;
}

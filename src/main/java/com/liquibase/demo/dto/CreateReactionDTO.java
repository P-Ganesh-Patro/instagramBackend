package com.liquibase.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReactionDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Comment ID is required")
    private Long commentId;

    @NotNull(message = "Reaction Type ID is required")
    private Long reactionTypeId;

    private String reactedType;
}

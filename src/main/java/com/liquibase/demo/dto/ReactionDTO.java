package com.liquibase.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO {
    private Long userId;

    private Long reactionTypeId;

    private Long commentId;

    private String reactedType;
}

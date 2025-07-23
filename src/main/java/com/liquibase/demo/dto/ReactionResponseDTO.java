package com.liquibase.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionResponseDTO {
    private Long id;
    private String userName;
    private String comment;
    private String reactionType;
    private String reactedType;
    private LocalDateTime createdAt;
}

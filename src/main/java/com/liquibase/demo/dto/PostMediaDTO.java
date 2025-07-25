package com.liquibase.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMediaDTO {
    private String mediaUrl;
    private String mediaType;
}

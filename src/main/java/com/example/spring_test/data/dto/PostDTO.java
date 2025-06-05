package com.example.spring_test.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer id;
    private String username;
    private String name;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

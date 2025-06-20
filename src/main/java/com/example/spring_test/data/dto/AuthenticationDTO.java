package com.example.spring_test.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;

    private String role;
    @NotNull
    private String name;
}

package com.example.spring_test.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authentication")
public class AuthenticationEntity {
    @Id
    @Size(max = 100)
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    private String role;


    @Size(max = 10)
    @NotNull
    @Column(name = "name", nullable = false, length = 10)
    private String name;

}
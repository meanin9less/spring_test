package com.example.spring_test.data.dao;

import com.example.spring_test.data.entity.AuthenticationEntity;
import com.example.spring_test.data.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationDAO {
    private final AuthenticationRepository authenticationRepository;

    public Boolean isExist(String username) {
        return this.authenticationRepository.existsById(username);
    }

    public AuthenticationEntity findById(String username) {
        return this.authenticationRepository.findById(username).orElse(null);
    }

    public void join(String username, String password, String role, String name) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthenticationEntity authenticationEntity = AuthenticationEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .name(name)
                .build();
        this.authenticationRepository.save(authenticationEntity);
    }

}

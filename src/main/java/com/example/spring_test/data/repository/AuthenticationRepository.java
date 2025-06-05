package com.example.spring_test.data.repository;

import com.example.spring_test.data.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String > {
    List<AuthenticationEntity> findNameByUsername(String username);
}

package com.example.spring_test.data.repository;

import com.example.spring_test.data.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}

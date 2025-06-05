package com.example.spring_test.data.dao;

import com.example.spring_test.data.entity.AuthenticationEntity;
import com.example.spring_test.data.entity.PostEntity;
import com.example.spring_test.data.repository.AuthenticationRepository;
import com.example.spring_test.data.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class PostDAO {
    private final PostRepository postRepository;
    private final AuthenticationRepository authenticationRepository;

    public List<PostEntity> findAll() {
        return this.postRepository.findAll();
    }

    public void saveNewPost(String title, String text, String username, LocalDateTime createdAt) {
        Optional<AuthenticationEntity> authenticationEntity = this.authenticationRepository.findById(username);
        if (authenticationEntity.isPresent()) {
            PostEntity post = PostEntity.builder()
                    .title(title)
                    .text(text)
                    .created(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .username(authenticationEntity.get())
                    .build();
            this.postRepository.save(post);
            return;
        }
        throw new EntityNotFoundException("username not found");
    }

    public void saveUpdatedPost(Integer id,String title, String text) {
            Optional<PostEntity> post = this.postRepository.findById(id);
            if (post.isPresent()) {
                PostEntity postEntity = post.get();
                postEntity.setTitle(title);
                postEntity.setText(text);
                postEntity.setUpdated(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                this.postRepository.save(postEntity);
                return;
        }
        throw new EntityNotFoundException("username not found");
    }

    public void deletePost(Integer id) {
        Optional<PostEntity> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            this.postRepository.delete(post.get());
            return;
        }
        throw new EntityNotFoundException("username not found");
    }



}

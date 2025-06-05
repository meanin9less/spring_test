package com.example.spring_test.controller;

import com.example.spring_test.data.dto.PostDTO;
import com.example.spring_test.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/postlist")
    public ResponseEntity<List<PostDTO>> findAll() {
        return ResponseEntity.ok(this.postService.findAll());
    }

    @PostMapping(value = "/newpost")
    public ResponseEntity<String> newPost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(this.postService.saveNewPost(postDTO));
    }

    @PostMapping(value = "/updatepost")
    public ResponseEntity<String> updatePost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(this.postService.saveUpdatedPost(postDTO));
    }

    @DeleteMapping(value = "/deletepost")
    public ResponseEntity<String> deletePost(@RequestParam String id, String username) {
         Integer postId = Integer.parseInt(id);
        return ResponseEntity.ok(this.postService.deletePost(postId, username));
    }
}

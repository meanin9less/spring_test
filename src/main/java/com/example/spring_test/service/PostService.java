package com.example.spring_test.service;

import com.example.spring_test.data.dao.PostDAO;
import com.example.spring_test.data.dto.PostDTO;
import com.example.spring_test.data.entity.PostEntity;
import com.example.spring_test.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDAO postDAO;

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = this.postDAO.findAll();
        List<PostDTO> postDTOList = new ArrayList<PostDTO>();
        for (PostEntity postEntity : postEntityList) {
            PostDTO postDTO = PostDTO.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .text(postEntity.getText())
                    .createdAt(postEntity.getCreated())
                    .updatedAt(postEntity.getUpdated())
                    .username(postEntity.getUsername().getUsername())
                    .name(postEntity.getUsername().getName())
                    .build();
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    public String saveNewPost(PostDTO postDTO) {
        this.postDAO.saveNewPost(
                postDTO.getTitle(),
                postDTO.getText(),
                postDTO.getUsername(),
                postDTO.getCreatedAt()
        );
        return "Post Saved";
    }

    public String saveUpdatedPost(PostDTO postDTO) {
        this.postDAO.saveUpdatedPost(
                postDTO.getId(),
                postDTO.getTitle(),
                postDTO.getText()
        );
        return "Post Updated";
    }

    public String deletePost(Integer id) {
        this.postDAO.deletePost(id);
        return "Post Deleted";
    }
}



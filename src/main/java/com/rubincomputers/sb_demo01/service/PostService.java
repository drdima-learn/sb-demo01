package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.repository.PostRepository;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Page<PostDTO> getAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostMapper::dto);
    }

    public Page<PostDTO> getByUserId(Pageable pageable) {
        return null;
    }
}

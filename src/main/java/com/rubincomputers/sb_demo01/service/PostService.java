package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.repository.PostRepository;
import com.rubincomputers.sb_demo01.repository.UserRepository;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<PostDTO> getAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostMapper::toDto);
    }

    public Page<Post> getAllWithUser(Pageable pageable) {
        Page<Post> all = postRepository.findAll(pageable);
        return all;
    }

    public Page<PostDTO> getPostsByUserId(long userId, Pageable pageable) {
        return postRepository.getByUserId(userId, pageable).map(PostMapper::toDto);
    }

    public PostDTO create(PostDTO postDTO, long userId) {
        Assert.notNull(postDTO, "postDTO must not be null");
        log.debug("request to save entity User : {}", postDTO);
        Post post = PostMapper.toEntity(postDTO);
        post.setUser(userRepository.getReferenceById(userId));
        post = postRepository.save(post);
        return PostMapper.toDto(post);
    }


    public PostDTO getPost(long postId, long userId) {
        return postRepository.getByIdAndUserId(postId, userId).map(PostMapper::toDto)
                .orElseThrow(() -> new NotFoundException("postId=" + postId + " , userId=" + userId));
    }


}

package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.web.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.rubincomputers.sb_demo01.web.controller.profile.ProfileRest.REST_URL;


@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRest extends ProfileAbstract {
    static final String REST_URL = "/rest/profile";

    @GetMapping(value = {"/posts"})
    public Page<PostDTO> getUserPosts(Pageable pageable) {
        onlyAllowedSortProperties(pageable, ALLOWED_ORDERED_PROPERTIES);
        long userId = SecurityUtil.authUserId();
        return postService.getPostsByUserId(userId, pageable);
    }

    @GetMapping(value = {"/posts/{postId}"})
    public PostDTO getPost(@PathVariable long postId) {
        long userId = SecurityUtil.authUserId();
        return postService.getPost(postId, userId);
    }

    @PostMapping(value = {"/posts"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO created = super.create(postDTO);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/posts/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}

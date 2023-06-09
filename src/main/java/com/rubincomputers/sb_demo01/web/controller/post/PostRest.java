package com.rubincomputers.sb_demo01.web.controller.post;

import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rubincomputers.sb_demo01.web.controller.post.PostRest.REST_URL;

@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PostRest extends PostAbstract {
    static final String REST_URL = "/rest/posts";


    @GetMapping(value = {"", "/"})
    public Page<PostDTO> getPosts(Pageable pageable) {
        onlyAllowedSortProperties(pageable, ALLOWED_ORDERED_PROPERTIES);
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return postService.getAll(pageable);
    }

}

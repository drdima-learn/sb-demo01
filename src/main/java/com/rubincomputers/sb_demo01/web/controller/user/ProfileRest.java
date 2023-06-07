package com.rubincomputers.sb_demo01.web.controller.user;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rubincomputers.sb_demo01.web.controller.user.ProfileRest.REST_URL;


@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRest extends ProfileAbstract {
    static final String REST_URL = "/rest/profile";

    @Autowired
    private PostService postService;

    @GetMapping(value = {"/posts", })
    public Page<PostDTO> getUserPosts(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable, ALLOWED_ORDERED_PROPERTIES)) {
            throw new BadSortParameter("Bad Parameter: " + pageable.getSort().toString());
        }
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return postService.getByUserId(pageable);
    }

}

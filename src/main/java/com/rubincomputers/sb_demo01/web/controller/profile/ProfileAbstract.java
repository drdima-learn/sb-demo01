package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import com.rubincomputers.sb_demo01.web.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class ProfileAbstract extends AbstractController {
    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "user_id"
            ));

    @Autowired
    protected PostService postService;

    protected PostDTO create(PostDTO postDTO) {
        long userId = SecurityUtil.authUserId();
        log.debug("REST request to save Post : {}", postDTO);
        ValidationUtil.checkNew(postDTO);
        return postService.create(postDTO, userId);
    }


}

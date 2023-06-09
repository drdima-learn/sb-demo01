package com.rubincomputers.sb_demo01.web.controller.post;

import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class PostAbstract extends AbstractController {
    public static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id"
            ));

    @Autowired
    protected PostService postService;


}

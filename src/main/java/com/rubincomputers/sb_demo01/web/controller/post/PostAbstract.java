package com.rubincomputers.sb_demo01.web.controller.post;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class PostAbstract extends AbstractController {
    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "login",
                    "firstName",
                    "lastName",
                    "email"
            ));

    @Autowired
    protected PostService postService;
}

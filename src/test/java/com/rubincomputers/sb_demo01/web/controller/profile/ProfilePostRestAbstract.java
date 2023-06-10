package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProfilePostRestAbstract extends ProfileRestAbstract {
    protected static final String REST_URL = ProfileRest.REST_URL + "/posts/";

    @Autowired
    protected PostService postService;

    @Test
    protected void emptyTest() {
    }

}

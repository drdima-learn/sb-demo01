package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AdminRestAbstract extends AbstractControllerTest {

    protected static final String REST_URL = AdminRest.REST_URL + '/';

    @Autowired
    protected UserService userService;

    @Test
    protected void emptyTest() {
    }


}

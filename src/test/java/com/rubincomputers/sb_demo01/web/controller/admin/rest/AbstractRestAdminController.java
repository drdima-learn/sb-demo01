package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRestAdminController extends AbstractControllerTest {

    protected static final String REST_URL = RestAdminController.REST_URL + '/';

    @Autowired
    protected UserService userService;

    @Test
    protected void emptyTest() {
    }
}

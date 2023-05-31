package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.AbstractController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public abstract class AbstractRestAdminControllerTest extends AbstractController {

    protected static final String REST_URL = RestAdminController.REST_URL + '/';

    @Autowired
    protected UserService userService;

    @Test
    protected void emptyTest() {
    }

    protected ResultMatcher expectRestException(Class<? extends Throwable> ex) {
        return jsonPath("$.exception", containsString(ex.getSimpleName()));
    }
}

package com.rubincomputers.sb_demo01.web.controller;

import com.rubincomputers.sb_demo01.web.AbstractController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class ResourceController extends AbstractController {
    @Test
    void resources() throws Exception {

        cssTest(HttpMethod.GET,
                "/css/style.css",
                HttpStatus.OK
        );
    }
}
package com.rubincomputers.sb_demo01.web.controller;

import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void resources() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("text/css")))
                .andExpect(status().isOk());
    }
}
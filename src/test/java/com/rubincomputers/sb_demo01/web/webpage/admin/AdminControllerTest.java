package com.rubincomputers.sb_demo01.web.webpage.admin;

import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AdminControllerTest extends AbstractControllerTest {

    private static final String WEBPAGE_URL = AdminController.WEBPAGE_URL + '/';

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
        //.andExpect(forwardedUrl("/template/users.html"))
        ;
    }
}
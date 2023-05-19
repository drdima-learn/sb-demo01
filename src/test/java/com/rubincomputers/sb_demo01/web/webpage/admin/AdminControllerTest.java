package com.rubincomputers.sb_demo01.web.webpage.admin;

import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import com.rubincomputers.sb_demo01.web.data.UserTestData;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
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
                .andExpect(view().name("users"));
    }

    @Test
    void getUsersWithBadSortParameter() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/?sort=id2,asc"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"))
                .andExpect(content().string(containsString("BadSortParameter")));

    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/" + UserTestData.USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(content().string(containsString("vasya@gmail.com")));
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/" + UserTestData.USER_ID_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"))
                .andExpect(content().string(containsString("NotFound")));
    }

    @Test
    void getUserByEmail() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/by-email?email=" + UserTestData.user1.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
                .andExpect(content().string(containsString("vasya@gmail.com")));
    }

    @Test
    void getUserByEmailNotFound() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/by-email?email=" + "notfound@gmail.com"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"))
                .andExpect(content().string(containsString("NotFoundException")));
    }

    @Test
    void getUserByEmailBadEmail() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/by-email?email=" + "bademailgmail.com"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("exception"))
                .andExpect(content().string(containsString("ConstraintViolationException")));

    }


}
package com.rubincomputers.sb_demo01.web.rest;

import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.rubincomputers.sb_demo01.web.data.UserTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RestAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestAdminController.REST_URL + '/';

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson("content", allUsers));
    }

    @Test
    void getAllUsersSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson("content", user1, user2, user3));
    }

    @Test
    void getAllUsersAsListSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/list/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(user1, user2, user3));
    }

    @Test
    void getAllUsersBadFieldSorted() throws Exception {
        mockMvc.perform(get(REST_URL + "/?page=0&size=3&sort=id2,asc")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.exception", containsString("BadSortParameter")));
    }
}
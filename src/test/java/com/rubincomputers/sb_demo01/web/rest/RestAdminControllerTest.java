package com.rubincomputers.sb_demo01.web.rest;

import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import com.rubincomputers.sb_demo01.web.data.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.rubincomputers.sb_demo01.web.data.UserTestData.USER_DTO_MATCHER;
import static com.rubincomputers.sb_demo01.web.data.UserTestData.user1;
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
                .andExpect(USER_DTO_MATCHER.contentJson("content", UserTestData.allUsers));
    }

    @Test
    void getAllUsersSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson("content", UserTestData.user1, UserTestData.user2, UserTestData.user3));
    }

    @Test
    void getAllUsersAsListSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/list/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(UserTestData.user1, UserTestData.user2, UserTestData.user3));
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

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + UserTestData.USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(UserTestData.user1));
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + UserTestData.USER_ID_NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUserByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "/by-email?email=" + user1.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(UserTestData.user1));
    }

    @Test
    void getUserByEmailNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/by-email?email=" + "notfound@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getUserByEmailWithBadEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "/by-email?email=" + "bademailgmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

    }

}
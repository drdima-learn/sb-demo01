package com.rubincomputers.sb_demo01.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubincomputers.sb_demo01.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.rubincomputers.sb_demo01.web.data.UserTestData.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@SpringBootTest(classes = { Main.class})
//@AutoConfigureMockMvc
public class RestAdminControllerTest {

    private static final String REST_URL = RestAdminController.REST_URL + '/';

    //@Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }


    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }


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
                //.andExpect(jsonPath("$.content", equalToObject(List.of(user1, user2, user3))));
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
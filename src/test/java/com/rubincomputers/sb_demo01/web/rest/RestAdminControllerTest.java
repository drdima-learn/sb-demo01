package com.rubincomputers.sb_demo01.web.rest;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.rubincomputers.sb_demo01.dto.UserDTO.dto;
import static com.rubincomputers.sb_demo01.web.data.UserTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RestAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestAdminController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson("content", allUsersDTO));
    }

    @Test
    void getAllUsersSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson("content", dto(user1), dto(user2), dto(user3)));
    }

    @Test
    void getAllUsersAsListSortedFirstThree() throws Exception {
        mockMvc.perform(get(REST_URL + "/list/?page=0&size=3&sort=id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(dto(user1), dto(user2), dto(user3)));
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
        mockMvc.perform(get(REST_URL + "/" + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(USER_DTO_MATCHER.contentJson(dto(user1)));
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER_ID_NOT_FOUND)
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
                .andExpect(USER_DTO_MATCHER.contentJson(dto(user1)));
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

    @Test
    void createNewUser() throws Exception {
        User newUser = getNew();
        UserRegistrationDTO newUserRegistrationDTO = UserRegistrationDTO.from(newUser);
        ResultActions action = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newUserRegistrationDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()); //201

        User created = USER_MATCHER.readFromJson(action);
        ValidationUtil.checkNotNew(created);
        long newId = created.getId();
        newUserRegistrationDTO.setId(newId);
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_DTO_MATCHER.assertMatch(userService.get(newId), UserDTO.dto(newUser));
    }

    private void create(User newUser) throws Exception {
        create(newUser, "MethodArgumentNotValidException");
    }

    private void create(User newUser, String exceptionStr) throws Exception {
        UserRegistrationDTO newUserRegistrationDTO = UserRegistrationDTO.from(newUser);
        ResultActions action = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newUserRegistrationDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()) //400
                .andExpect(jsonPath("$.exception", containsString(exceptionStr)));
    }

    @Test
    void createWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("wronggmail.com");
        create(newUser);

    }

    @Test
    void createBlankEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("");
        create(newUser);

    }

    @Test
    void createBlankFirstName() throws Exception {
        User newUser = getNew();
        newUser.setFirstName(" ");
        create(newUser);
    }

    @Test
    void createBlankLastName() throws Exception {
        User newUser = getNew();
        newUser.setLastName("");
        create(newUser);
    }

    @Test
    void createNewWithId() throws Exception {
        User newUser = getNew();
        newUser.setId(123L);
        create(newUser, "IllegalRequestDataException");
    }

    @Test
    void createDuplicateEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("vasya@gmail.com");
        create(newUser, "DataIntegrityViolationException");
    }
}
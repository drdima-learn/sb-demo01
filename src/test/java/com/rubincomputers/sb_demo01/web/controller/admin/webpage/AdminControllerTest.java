package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import com.rubincomputers.sb_demo01.data.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import static com.rubincomputers.sb_demo01.data.UserTestData.getNew;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @Test
    void register() throws Exception {
        mockMvc.perform(get(WEBPAGE_URL + "/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"));

    }


    private void saveRegister(User newUser, boolean expectRedirect) throws Exception {
        saveRegister(newUser, expectRedirect, "");
    }
    private void saveRegister(User newUser, String errorMsg) throws Exception {
        saveRegister(newUser, false, errorMsg);
    }

    private void saveRegister(User newUser, boolean expectRedirect, String errorMsg) throws Exception {
        // https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/

        UserRegistrationDTO dto = UserRegistrationDTO.from(newUser);

        MultiValueMap<String, String> formData = UserRegistrationDTO.toMultiValueMap(dto);


        MockHttpServletRequestBuilder requestBuilder = post(WEBPAGE_URL + "/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(formData);


        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        if (!expectRedirect) {
            resultActions
                    .andExpect(status().isOk())
                    //check that webpage contains an error message on email field
                    .andExpect(content().string(containsString(errorMsg)));
        } else {
            resultActions
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", containsString("status=ok")));
        }
    }


    @Test
    void saveUser() throws Exception {
        User newUser = getNew();
        saveRegister(newUser, true);
    }

    @Test
    void saveUserWithWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("wronggmail.com");
        saveRegister(newUser, "must be a well-formed email address");
    }

    @Test
    void saveUserWithBlankEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("");
        saveRegister(newUser, "must not be blank");
    }
}
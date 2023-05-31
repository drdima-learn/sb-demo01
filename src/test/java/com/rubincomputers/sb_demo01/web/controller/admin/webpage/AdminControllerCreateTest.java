package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import static com.rubincomputers.sb_demo01.data.UserTestData.getNew;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerCreateTest extends AbstractAdminControllerTest {


    private void saveRegister(User newUser, boolean expectRedirect) throws Exception {
        saveRegister(newUser, expectRedirect, "");
    }

    private void saveRegister(User newUser, String errorMsg) throws Exception {


        UserRegistrationDTO dto = UserRegistrationDTO.from(newUser);
        MultiValueMap<String, String> formData = UserRegistrationDTO.toMultiValueMap(dto);


//        pageTest(HttpMethod.POST,
//                WEBPAGE_URL + "/register",
//                formData,
//                HttpStatus.OK,
//                "exception",
//                errorMsg
//        );

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



        UserRegistrationDTO dto = UserRegistrationDTO.from(newUser);
        MultiValueMap<String, String> formData = UserRegistrationDTO.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.FOUND, //301 redirect
                header().string("Location", containsString("status=ok"))
        );



//        User newUser = getNew();
//        saveRegister(newUser, true);






    }

    @Test
    void saveUserWithWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("wronggmail.com");
        //saveRegister(newUser, "must be a well-formed email address");

        UserRegistrationDTO dto = UserRegistrationDTO.from(newUser);
        MultiValueMap<String, String> formData = UserRegistrationDTO.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.OK,
                "register",
                "must be a well-formed email address"
        );
    }

    @Test
    void saveUserWithBlankEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("");

        //saveRegister(newUser, "must not be blank");

        // https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/

        UserRegistrationDTO dto = UserRegistrationDTO.from(newUser);
        MultiValueMap<String, String> formData = UserRegistrationDTO.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.OK,
                "register",
                "must not be blank"
        );
    }
}

package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

import static com.rubincomputers.sb_demo01.data.UserTestData.getNew;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

public class AdminControllerCreateTest extends AbstractAdminControllerTest {
    @Test
    void saveUser() throws Exception {
        User newUser = getNew();
        UserFormDTO dto = UserFormDTO.from(newUser);
        MultiValueMap<String, String> formData = UserFormDTO.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.FOUND, //301 redirect
                header().string("Location", containsString("status=ok"))
        );
    }

    @Test
    void saveUserWithWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("wronggmail.com");

        UserFormDTO dto = UserFormDTO.from(newUser);
        MultiValueMap<String, String> formData = UserFormDTO.toMultiValueMap(dto);

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

        // https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/

        UserFormDTO dto = UserFormDTO.from(newUser);
        MultiValueMap<String, String> formData = UserFormDTO.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.OK,
                "register",
                "must not be blank"
        );
    }
}

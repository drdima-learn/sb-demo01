package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
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
        UserFormDTO dto = UserMapper.toUserFormDTO(newUser);
        MultiValueMap<String, String> formData = UserMapper.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.FOUND, //302 redirect
                header().string("Location", containsString("status=ok"))
        );
    }

    @Test
    void saveUserWithWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("wronggmail.com");

        UserFormDTO dto = UserMapper.toUserFormDTO(newUser);
        MultiValueMap<String, String> formData = UserMapper.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.OK,
                "userForm",
                "must be a well-formed email address"
        );
    }

    @Test
    void saveUserWithBlankEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("");

        // https://rieckpil.de/test-thymeleaf-controller-endpoints-with-spring-boot-and-mockmvc/

        UserFormDTO dto = UserMapper.toUserFormDTO(newUser);
        MultiValueMap<String, String> formData = UserMapper.toMultiValueMap(dto);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/register",
                formData,
                HttpStatus.OK,
                "userForm",
                "must not be blank"
        );
    }
}

package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.service.mapper.UserMapper.dto;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

public class AdminControllerUpdateTest extends AbstractAdminControllerTest {

    @Autowired
    UserService userService;
    @Test
    void updateUserWithId() throws Exception {
        MultiValueMap<String, String> formDataWithId = UserMapper.fromUserToMultiValueMap(getUpdatedWithId());

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/edit/" + USER_ID,
                formDataWithId,
                HttpStatus.FOUND, //302 redirect,
                header().string("Location", containsString("status=ok"))

        );

        UserDTO actual = userService.getUserDTOById(USER_ID);
        USER_DTO_MATCHER.assertMatch(actual, dto(getUpdatedWithId()));
    }

    @Test
    void updateUserWithoutId() throws Exception {
        MultiValueMap<String, String> formDataWoId = UserMapper.fromUserToMultiValueMap(getUpdatedWoId());

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/edit/" + USER_ID,
                formDataWoId,
                HttpStatus.FOUND, //302 redirect,
                header().string("Location", containsString("status=ok"))

        );

        UserDTO actual = userService.getUserDTOById(USER_ID);
        USER_DTO_MATCHER.assertMatch(actual, dto(getUpdatedWithId()));
    }

    @Test
    void updateUserWithIdBlankFirstName() throws Exception {

        UserDTO expected = userService.getUserDTOById(USER_ID);

        User userWithId = getUpdatedWithId();
        userWithId.setFirstName("");
        MultiValueMap<String, String> formDataWithId = UserMapper.fromUserToMultiValueMap(userWithId);

        pageTest(HttpMethod.POST,
                WEBPAGE_URL + "/edit/" + USER_ID,
                formDataWithId,
                HttpStatus.OK,
                "userForm",
                "must not be blank"

        );

        UserDTO actual = userService.getUserDTOById(USER_ID);
        USER_DTO_MATCHER.assertMatch(actual, expected);
    }



}

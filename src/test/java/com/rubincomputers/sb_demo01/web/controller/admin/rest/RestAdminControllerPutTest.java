package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;

public class RestAdminControllerPutTest extends AbstractRestAdminControllerTest{
    @Test
    void updateUserWithoutIdWithIdInUrl() throws Exception {
        User updatedWithoutId = getUpdateWoId();
        UserFormDTO updatedWithoutIdFormDTO = UserMapper.from(updatedWithoutId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID,
                JsonUtil.writeValue(updatedWithoutIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        User expectedUpdatedWithId = getUpdateWoId();
        expectedUpdatedWithId.setId(USER_ID);
        USER_DTO_MATCHER.assertMatch(userService.getById(USER_ID), UserMapper.dto(expectedUpdatedWithId));
    }

    @Test
    void updateUserWithIdAndWithIdInUrl() throws Exception {
        User updatedWoId = getUpdateWoId();
        updatedWoId.setId(USER_ID);
        UserFormDTO updatedWithIdFormDTO = UserMapper.from(updatedWoId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID,
                JsonUtil.writeValue(updatedWithIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        USER_DTO_MATCHER.assertMatch(userService.getById(USER_ID), UserMapper.dto(updatedWoId));
    }

    // user with id, but wrong id (exists) in url. should get IllegalRequestDataException
    @Test
    void updateUserWithIdButWrongExistsIdInUrl() throws Exception {
        User updatedWithId = getUpdateWoId();
        updatedWithId.setId(USER_ID);
        UserFormDTO updatedWithIdFormDTO = UserMapper.from(updatedWithId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + (USER_ID+1), //wrong id
                JsonUtil.writeValue(updatedWithIdFormDTO),
                HttpStatus.BAD_REQUEST,
                expectRestException(IllegalRequestDataException.class) //determine that id in object, and id in url are different
        );
    }

    // ??, without id, with WRONG (but exists) id in url. it save it as normal, because cannot determine to which id data belongs.
    @Test
    void updateUserWithoutIdButWrongExistsIdInUrl() throws Exception {
        User updatedWoId = getUpdateWoId();

        UserFormDTO updatedWoIdFormDTO = UserMapper.from(updatedWoId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + (USER_ID+1),
                JsonUtil.writeValue(updatedWoIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        User expectedUpdatedWithId = getUpdateWoId();
        expectedUpdatedWithId.setId(USER_ID+1);
        USER_DTO_MATCHER.assertMatch(userService.getById(USER_ID+1), UserMapper.dto(expectedUpdatedWithId));
    }

    // without id, with WRONG (not exists) id in url. it should throw exception NotFound
    @Test
    void updateUserWithoutIdButWrongNotExistsIdInUrl() throws Exception {
        User updatedWoId = getUpdateWoId();

        UserFormDTO updatedWoIdFormDTO = UserMapper.from(updatedWoId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID_NOT_EXISTS,
                JsonUtil.writeValue(updatedWoIdFormDTO),
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    // without id, blank firstName, right id in url
    @Test
    void updateUserWithoutIdWithBlankFirstName() throws Exception {
        User updatedWoId = getUpdateWoId();
        updatedWoId.setFirstName("");

        UserFormDTO updatedWoIdFormDTO = UserMapper.from(updatedWoId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID,
                JsonUtil.writeValue(updatedWoIdFormDTO),
                HttpStatus.BAD_REQUEST,
                expectRestException(MethodArgumentNotValidException.class)
        );
    }

    // with id, but no id in url
    @Test
    void updateUserWithIdButNoIdInUrl() throws Exception {
        User updatedWoId = getUpdateWoId();
        updatedWoId.setId(USER_ID);

        UserFormDTO updatedWoIdFormDTO = UserMapper.from(updatedWoId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL,
                JsonUtil.writeValue(updatedWoIdFormDTO),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }
}

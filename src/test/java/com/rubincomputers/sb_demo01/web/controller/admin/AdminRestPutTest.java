package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;

public class AdminRestPutTest extends AdminRestAbstract {
    @Test
    void updateUserWithoutIdWithIdInUrl() throws Exception {
        User updatedWithoutId = getUpdatedWoId();
        UserFormDTO updatedWithoutIdFormDTO = UserMapper.toUserFormDTO(updatedWithoutId);

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID,
                JsonUtil.writeValue(updatedWithoutIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        User expectedUpdatedWithId = getUpdatedWithId();
        expectedUpdatedWithId.setId(USER_ID);
        USER_DTO_MATCHER.assertMatch(userService.getUserDTOById(USER_ID), UserMapper.toDto(expectedUpdatedWithId));
    }

    @Test
    void updateUserWithIdAndWithIdInUrl() throws Exception {
        UserFormDTO updatedWithIdFormDTO = UserMapper.toUserFormDTO(getUpdatedWithId());

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + USER_ID,
                JsonUtil.writeValue(updatedWithIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        USER_DTO_MATCHER.assertMatch(userService.getUserDTOById(USER_ID), UserMapper.toDto(getUpdatedWithId()));
    }

    // user with id, but wrong id (exists) in url. should get IllegalRequestDataException
    @Test
    void updateUserWithIdButWrongExistsIdInUrl() throws Exception {
        UserFormDTO updatedWithIdFormDTO = UserMapper.toUserFormDTO(getUpdatedWithId());

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + (USER_ID + 1), //wrong id
                JsonUtil.writeValue(updatedWithIdFormDTO),
                HttpStatus.BAD_REQUEST,
                expectRestException(IllegalRequestDataException.class) //determine that id in object, and id in url are different
        );
    }

    // ??, without id, with WRONG (but exists) id in url. it save it as normal, because cannot determine to which id data belongs.
    @Test
    void updateUserWithoutIdButWrongExistsIdInUrl() throws Exception {
        UserFormDTO updatedWoIdFormDTO = UserMapper.toUserFormDTO(getUpdatedWoId());

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL + (USER_ID + 1),
                JsonUtil.writeValue(updatedWoIdFormDTO),
                HttpStatus.NO_CONTENT
        );

        User expectedUpdatedWithId = getUpdatedWoId();
        expectedUpdatedWithId.setId(USER_ID + 1);
        USER_DTO_MATCHER.assertMatch(userService.getUserDTOById(USER_ID + 1), UserMapper.toDto(expectedUpdatedWithId));
    }

    // without id, with WRONG (not exists) id in url. it should throw exception NotFound
    @Test
    void updateUserWithoutIdButWrongNotExistsIdInUrl() throws Exception {
        UserFormDTO updatedWoIdFormDTO = UserMapper.toUserFormDTO(getUpdatedWoId());

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
        User updatedWoId = getUpdatedWoId();
        updatedWoId.setFirstName("");

        UserFormDTO updatedWoIdFormDTO = UserMapper.toUserFormDTO(updatedWoId);

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
        UserFormDTO updatedWithIdFormDTO = UserMapper.toUserFormDTO(getUpdatedWithId());

        ResultActions action = restTest(
                HttpMethod.PUT,
                REST_URL,
                JsonUtil.writeValue(updatedWithIdFormDTO),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }
}

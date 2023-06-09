package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;


public class AdminRestCreateTest extends AdminRestAbstract {

    @Test
    void createNewUser() throws Exception {
        User newUser = getNew();
        UserFormDTO newUserFormDTO = UserMapper.toUserFormDTO(newUser);

        ResultActions action = restTest(
                HttpMethod.POST,
                REST_URL,
                JsonUtil.writeValue(newUserFormDTO),
                HttpStatus.CREATED
        );

        UserDTO created = USER_DTO_MATCHER.readFromJson(action);
        ValidationUtil.checkNotNew(created);
        long newId = created.getId();
        newUserFormDTO.setId(newId);
        newUser.setId(newId);
        USER_DTO_MATCHER.assertMatch(created, UserMapper.toDto(newUser));
        USER_DTO_MATCHER.assertMatch(userService.getUserDTOById(newId), UserMapper.toDto(newUser));
    }


    @Test
    void createWrongEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail(USER_EMAIL_NOT_WELL_FORMED);
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
    void createNewUserWithId() throws Exception {
        User newUser = getNew();
        newUser.setId(USER_ID_NOT_EXISTS);
        create(newUser, IllegalRequestDataException.class);
    }

    @Test
    void createNewUserWithExistingId() throws Exception {
        User newUser = getNew();
        newUser.setId(USER_ID);
        create(newUser, IllegalRequestDataException.class);
    }

    @Test
    void createNewUserWithDuplicateEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail(USER_EMAIL);
        create(newUser, DataIntegrityViolationException.class);
    }


    private void create(User newUser) throws Exception {
        create(newUser, MethodArgumentNotValidException.class);
    }

    private void create(User newUser, Class<? extends Throwable> ex) throws Exception {
        UserFormDTO newUserFormDTO = UserMapper.toUserFormDTO(newUser);
        ResultActions action = restTest(
                HttpMethod.POST,
                REST_URL,
                JsonUtil.writeValue(newUserFormDTO),
                HttpStatus.BAD_REQUEST,
                ex
        );
    }
}
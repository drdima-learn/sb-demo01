package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.web.AbstractControllerTest;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.dto.UserDTO.dto;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RestAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestAdminController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "",
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson("content", allUsersDTO)
        );
    }

    @Test
    void getAllUsersSortedFirstThree() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/?page=0&size=3&sort=id,asc",
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson("content", dto(user1), dto(user2), dto(user3))
        );
    }

    @Test
    void getAllUsersAsListSortedFirstThree() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/list/?page=0&size=3&sort=id,asc",
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(dto(user1), dto(user2), dto(user3))
        );
    }

    @Test
    void getAllUsersWithBadSortedField() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/?page=0&size=3&sort=id2,asc",
                HttpStatus.BAD_REQUEST,
                expectRestException(BadSortParameter.class)
        );
    }


    @Test
    void getUserById() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/" + USER_ID,
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(dto(user1))
        );
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/" + USER_ID_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    @Test
    void getUserByEmail() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + user1.getEmail(),
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(dto(user1))
        );
    }

    @Test
    void getUserByEmailNotFound() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + "notfound@gmail.com",
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    @Test
    void getUserByEmailWithBadEmail() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + "bademailgmail.com",
                HttpStatus.BAD_REQUEST,
                expectRestException(ConstraintViolationException.class)
        );
    }

    @Test
    void createNewUser() throws Exception {

        User newUser = getNew();
        UserRegistrationDTO newUserRegistrationDTO = UserRegistrationDTO.from(newUser);

        ResultActions action  = restTest(
                HttpMethod.POST,
                REST_URL,
                JsonUtil.writeValue(newUserRegistrationDTO),
                HttpStatus.CREATED
        );

        User created = USER_MATCHER.readFromJson(action);
        ValidationUtil.checkNotNew(created);
        long newId = created.getId();
        newUserRegistrationDTO.setId(newId);
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_DTO_MATCHER.assertMatch(userService.getById(newId), UserDTO.dto(newUser));
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
        newUser.setId(USER_ID_NOT_FOUND);
        create(newUser, IllegalRequestDataException.class);
    }

    @Test
    void createWithExistingId() throws Exception {
        User newUser = getNew();
        newUser.setId(USER_ID);
        create(newUser, IllegalRequestDataException.class);
    }

    @Test
    void createDuplicateEmail() throws Exception {
        User newUser = getNew();
        newUser.setEmail("vasya@gmail.com");
        create(newUser, DataIntegrityViolationException.class);
    }


    @Test
    void deleteById() throws Exception {
        restTest(HttpMethod.DELETE,
                REST_URL + USER_ID,
                HttpStatus.NO_CONTENT
        );

        assertThrows(NotFoundException.class, () -> userService.getById(USER_ID));
    }

    @Test
    void deleteByIdWithNotExistsId() throws Exception {
        restTest(HttpMethod.DELETE,
                REST_URL + USER_ID_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    @Test
    void deleteByEmail() {
    }


    private void create(User newUser) throws Exception {
        create(newUser, MethodArgumentNotValidException.class);
    }

    private void create(User newUser, Class<? extends Throwable> ex) throws Exception {
        UserRegistrationDTO newUserRegistrationDTO = UserRegistrationDTO.from(newUser);
        ResultActions action  = restTest(
                HttpMethod.POST,
                REST_URL,
                JsonUtil.writeValue(newUserRegistrationDTO),
                HttpStatus.BAD_REQUEST,
                expectRestException(ex)
        );
    }

    private ResultMatcher expectRestException(Class<? extends Throwable> ex) {
        return jsonPath("$.exception", containsString(ex.getSimpleName()));
    }
}
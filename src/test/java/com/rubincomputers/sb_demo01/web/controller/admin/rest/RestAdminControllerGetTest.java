package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.dto.UserDTO.dto;

public class RestAdminControllerGetTest extends AbstractRestAdminControllerTest {
    @Test
    void getAllUsers() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL,
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
    void getUserByIdNotExists() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/" + USER_ID_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    @Test
    void getUserByEmail() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL,
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(dto(user1))
        );
    }

    @Test
    void getUserByEmailNotExists() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                expectRestException(NotFoundException.class)
        );
    }

    @Test
    void getUserByEmailWithNotWellFormed() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL_NOT_WELL_FORMED,
                HttpStatus.BAD_REQUEST,
                expectRestException(ConstraintViolationException.class)
        );
    }
}
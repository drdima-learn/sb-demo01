package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.data.UserTestData;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;

public class AdminControllerGetTest extends AbstractAdminControllerTest {

    @Test
    void getUsers() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL,
                HttpStatus.OK,
                "users"
        );
    }

    @Test
    void getUsersWithBadSortParameter() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/?sort=id2,asc",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                BadSortParameter.class
        );
    }

    @Test
    void getUserById() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/" + UserTestData.USER_ID,
                HttpStatus.OK,
                "user",
                "vasya@gmail.com"
        );
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/" + UserTestData.USER_ID_NOT_EXISTS,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                NotFoundException.class
        );
    }

    @Test
    void getUserByEmail() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/by-email?email=" + USER_EMAIL,
                HttpStatus.OK,
                "user",
                USER_EMAIL
        );
    }

    @Test
    void getUserByEmailNotExists() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/by-email?email=" + USER_EMAIL_NOT_EXISTS,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                NotFoundException.class
        );
    }

    @Test
    void getUserByEmailNotWellFormed() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/by-email?email=" + USER_EMAIL_NOT_WELL_FORMED,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                ConstraintViolationException.class
        );
    }

    @Test
    void register() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/register",
                HttpStatus.OK,
                "userForm"
        );
    }

}

package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import static com.rubincomputers.sb_demo01.data.PostTestData.*;
import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.service.mapper.UserMapper.toDto;


public class AdminRestGetTest extends AdminRestAbstract {
    @Test
    void getAllUsersWoSortParameters() throws Exception {
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
                USER_DTO_MATCHER.contentJson("content", UserMapper.toDto(user1), UserMapper.toDto(user2), UserMapper.toDto(user3))
        );
    }

    @Test
    void getAllUsersAsListSortedFirstThree() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/list/?page=0&size=3&sort=id,asc",
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(UserMapper.toDto(user1), UserMapper.toDto(user2), UserMapper.toDto(user3))
        );
    }

    @Test
    void getAllUsersWithWrongSortedField() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/?page=0&size=3&sort=id2,asc",
                HttpStatus.BAD_REQUEST,
                BadSortParameter.class
        );
    }

    @Test
    void getAllUsersWithNormalSortedField() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/?page=0&size=3&sort=firstName,asc",
                HttpStatus.OK
        );
    }


    @Test
    void getUserById() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/" + USER_ID,
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(UserMapper.toDto(user1))
        );
    }

    @Test
    void getUserByIdNotExists() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/" + USER_ID_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                NotFoundException.class
        );
    }

    @Test
    void getUserByEmail() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL,
                HttpStatus.OK,
                USER_DTO_MATCHER.contentJson(UserMapper.toDto(user1))
        );
    }

    @Test
    void getUserByEmailNotExists() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                NotFoundException.class
        );
    }

    @Test
    void getUserByEmailWithNotWellFormed() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "/by-email?email=" + USER_EMAIL_NOT_WELL_FORMED,
                HttpStatus.BAD_REQUEST,
                ConstraintViolationException.class
        );
    }

    @Test
    void getPostsByUserId() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + USER_ID + "/posts",
                HttpStatus.OK,
                POST_DTO_MATCHER.contentJson("content", PostMapper.toDto(post1), PostMapper.toDto(post2), PostMapper.toDto(post3))
        );
    }

    @Test
    void getPostsEmptyByUserId() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + USER_ID_2 + "/posts",
                HttpStatus.OK,
                POST_DTO_MATCHER.contentJson("content") //expect empty array
        );
    }
}

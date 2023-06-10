package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.data.PostTestData;
import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import static com.rubincomputers.sb_demo01.data.PostTestData.*;
import static com.rubincomputers.sb_demo01.data.UserTestData.*;

public class ProfilePostGetRestTest extends ProfilePostRestAbstract {
    @Test
    void getPost() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + POST_ID,
                HttpStatus.OK,
                POST_DTO_MATCHER.contentJson(PostMapper.toDto(post1))
        );
    }

    @Test
    void getPostNotExists() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + POST_ID_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                NotFoundException.class
        );
    }

    @Test
    void getUserPosts() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL,
                HttpStatus.OK,
                POST_DTO_MATCHER.contentJson("content", PostMapper.toDto(post1), PostMapper.toDto(post2), PostMapper.toDto(post3))
        );
    }

    @Test
    void getUserPostsWithSort() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "?page=0&size=3&sort=id,desc",
                HttpStatus.OK,
                POST_DTO_MATCHER.contentJson("content", PostMapper.toDto(post3), PostMapper.toDto(post2), PostMapper.toDto(post1))
        );
    }

    @Test
    void getUserPostsWithSortParameterWrong() throws Exception {
        restTest(HttpMethod.GET,
                REST_URL + "?page=0&size=3&sort=id2,desc",
                HttpStatus.BAD_REQUEST,
                BadSortParameter.class
        );
    }
}

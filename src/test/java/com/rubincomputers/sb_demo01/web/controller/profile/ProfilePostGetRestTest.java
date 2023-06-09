package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.data.PostTestData;
import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.mapper.PostMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
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
}

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

import static com.rubincomputers.sb_demo01.data.PostTestData.POST_DTO_MATCHER;
import static com.rubincomputers.sb_demo01.data.UserTestData.*;

public class ProfilePostCreateRestTest extends ProfilePostRestAbstract {

    @Test
    void createNewPost() throws Exception {
        Post newPost = PostTestData.getNew();
        PostDTO newPostDTO = PostMapper.toDto(newPost);

        ResultActions action = restTest(
                HttpMethod.POST,
                REST_URL,
                JsonUtil.writeValue(newPostDTO),
                HttpStatus.CREATED
        );

        PostDTO created = POST_DTO_MATCHER.readFromJson(action);
        ValidationUtil.checkNotNew(created);
        long newId = created.getId();
        newPostDTO.setId(newId);
        newPost.setId(newId);
        POST_DTO_MATCHER.assertMatch(created, newPostDTO);
        POST_DTO_MATCHER.assertMatch(postService.getPost(newId, USER_ID), newPostDTO);
    }
}

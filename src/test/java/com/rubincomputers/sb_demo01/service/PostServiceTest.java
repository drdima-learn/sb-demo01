package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class PostServiceTest extends AbstractServiceTest {


    @Autowired
    private PostService service;

    @Test
    void getAllWithUser() {
        Page<Post> all = service.getAllWithUser(PageRequest.of(0, 3));
        //POST_MATCHER.assertMatch(all.getContent(), allUsersDTO);
        System.out.println(all);
    }
}

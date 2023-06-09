package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.controller.AbstractControllerTest;
import com.rubincomputers.sb_demo01.web.controller.admin.AdminRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProfileRestAbstract extends AbstractControllerTest {
    protected static final String REST_URL = ProfileRest.REST_URL + '/';



    @Test
    protected void emptyTest() {
    }

}

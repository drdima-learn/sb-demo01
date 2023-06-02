package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractAdminController {

    @Autowired
    protected UserService userService;

    public User create(UserFormDTO userFormDTO){
        User user = UserMapper.toUser(userFormDTO);
        log.debug("request to save entity User : {}", user);
        ValidationUtil.checkNew(user);
        return userService.create(user);

    }

}

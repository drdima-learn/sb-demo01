package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class AdminAbstract extends AbstractController {
    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "login",
                    "firstName",
                    "lastName",
                    "email"
            ));

    @Autowired
    protected UserService userService;


    public UserDTO create(UserFormDTO userFormDTO) {
        User user = UserMapper.toEntity(userFormDTO);
        ValidationUtil.checkNew(user);
        user = userService.create(user);
        return UserMapper.toDto(user);
    }

    public void update(@Valid UserFormDTO userFormDTO, long id) {
        log.debug("update userFormDTO: {}", userFormDTO);
        ValidationUtil.assureIdConsistent(userFormDTO, id);
        userService.update(userFormDTO);
    }

}

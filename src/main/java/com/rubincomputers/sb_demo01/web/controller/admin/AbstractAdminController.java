package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class AbstractAdminController extends AbstractController {

    @Autowired
    protected UserService userService;



    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "login",
                    "firstName",
                    "lastName",
                    "email"
            ));


    public User create(UserFormDTO userFormDTO) {
        User user = UserMapper.toUser(userFormDTO);
        log.debug("request to save entity User : {}", user);
        ValidationUtil.checkNew(user);
        return userService.create(user);

    }

    public void update(@Valid UserFormDTO userFormDTO, long id) {
        log.debug("update userFormDTO: {}", userFormDTO);
        ValidationUtil.assureIdConsistent(userFormDTO, id);
        userService.update(userFormDTO);
    }

}

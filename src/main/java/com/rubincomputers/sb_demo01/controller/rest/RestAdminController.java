package com.rubincomputers.sb_demo01.controller.rest;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/rest/admin/users")
    public List<UserDTO> getUsers() {
        return UserDTO.from(userService.getAll());
    }


}

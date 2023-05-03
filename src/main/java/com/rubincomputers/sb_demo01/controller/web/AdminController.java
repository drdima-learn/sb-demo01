package com.rubincomputers.sb_demo01.controller.web;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin/users")
    public String getUsers(Model model){
        List<UserDTO> users =  UserDTO.from(userService.getAll());
        model.addAttribute("users", users);
        return "users";
    }
}

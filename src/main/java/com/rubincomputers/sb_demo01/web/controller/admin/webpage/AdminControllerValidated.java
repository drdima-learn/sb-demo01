package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.controller.admin.AbstractAdminController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


@Controller
@Slf4j
@RequestMapping(value = AdminControllerValidated.WEBPAGE_URL)
@Validated //for checking email and other constrains
public class AdminControllerValidated extends AbstractAdminController {

    static final String WEBPAGE_URL = "/admin/users";
    @Autowired
    private UserService userService;


    @GetMapping(value = { "/by-email"})
    public String getUserByEmail(Model model, @RequestParam @Email String email) {
        UserDTO userDTO = userService.getByEmail(email);
        model.addAttribute("user", userDTO);
        return "user";
    }
}

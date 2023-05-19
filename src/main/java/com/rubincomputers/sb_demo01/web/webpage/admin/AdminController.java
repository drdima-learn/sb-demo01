package com.rubincomputers.sb_demo01.web.webpage.admin;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = AdminController.WEBPAGE_URL)
public class AdminController {

    static final String WEBPAGE_URL = "/admin/users";
    @Autowired
    private UserService userService;

    @GetMapping(value = {"", "/"})
    public String getUsers(Model model, Pageable pageable) {
        Page<UserDTO> userDTOPage = userService.getAll(pageable);
        model.addAttribute("users", userDTOPage);
        return "users";
    }

    @GetMapping(value = { "/{id}"})
    public String getUserById(Model model, @PathVariable Long id) {
        UserDTO userDTO = userService.get(id);
        model.addAttribute("user", userDTO);
        return "user";
    }

    @GetMapping(value = { "/by-email?email={email}"})
    //TODO check here @RequestParam
    public String getUserByEmail(Model model, @PathVariable String email) {
        UserDTO userDTO = userService.getByEmail(email);
        model.addAttribute("user", userDTO);
        return "user";
    }
}

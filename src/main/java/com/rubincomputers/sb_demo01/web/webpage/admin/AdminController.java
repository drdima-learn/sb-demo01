package com.rubincomputers.sb_demo01.web.webpage.admin;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;



@Controller
@Slf4j
@RequestMapping(value = AdminController.WEBPAGE_URL)
//@Validated //for checking email and other constrains
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


    @GetMapping(value = {"/register"})
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping(value = {"/register"})
    public String saveRegister(@Valid User user , BindingResult result) {
        log.debug("WEB request to save User : {}", user);
        if (result.hasErrors()) {
            log.debug("Error");
            return "register";
        }
        return "register";
    }
}

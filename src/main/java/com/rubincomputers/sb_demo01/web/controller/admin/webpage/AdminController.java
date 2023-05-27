package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.web.controller.admin.AbstractAdminController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
@RequestMapping(value = AdminController.WEBPAGE_URL)
//@Validated //for checking email and other constrains
public class AdminController extends AbstractAdminController {

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
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }


    @PostMapping(value = {"/register"})
    public String saveRegister(@Valid UserRegistrationDTO userRegistrationDTO , BindingResult result) {

        if (result.hasErrors()) {
            log.debug("User form has an errors");
            return "register";
        }
        try {
            super.create(UserRegistrationDTO.toUser(userRegistrationDTO));
            return "redirect:" + WEBPAGE_URL + "/register?status=ok&email=" + userRegistrationDTO.getEmail();
        } catch (DataIntegrityViolationException ex){
            result.rejectValue("email", "exception.user.duplicateEmail");
            return "register";
        }
    }

    @GetMapping(value = {"/delete/{id}"})
    public String deleteUserById(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:" + WEBPAGE_URL;
    }
}

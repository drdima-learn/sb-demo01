package com.rubincomputers.sb_demo01.web.controller.admin.webpage;

import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.web.controller.admin.AbstractAdminController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping(value = AdminController.WEBPAGE_URL)
//@Validated //for checking email and other constrains
public class AdminController extends AbstractAdminController {

    static final String WEBPAGE_URL = "/admin/users";
    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder (WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd"), true));
    }

    @GetMapping(value = {"", "/"})
    public String getUsers(Model model, Pageable pageable) {
        Page<UserDTO> userDTOPage = userService.getAll(pageable);
        model.addAttribute("users", userDTOPage);
        return "users";
    }

    @GetMapping(value = {"/{id}"})
    public String getUserById(Model model, @PathVariable Long id) {
        UserDTO userDTO = userService.getUserDTOById(id);
        model.addAttribute("user", userDTO);
        return "user";
    }


    @GetMapping(value = {"/register"})
    public String register(Model model) {
        model.addAttribute("userFormDTO", new UserFormDTO());
        model.addAttribute("isEdit", false);
        return "userForm";
    }


    @PostMapping(value = {"/register"})
    public String saveRegister(@Valid UserFormDTO userFormDTO, BindingResult result, Model model, @RequestParam Map<String, String> params) {

        if (result.hasErrors()) {
            return logAndReturnUserForm(model, false);
        }

        try {
            super.create(userFormDTO);
             return "redirect:" + WEBPAGE_URL + getUrlWithSortParams(params) + (getUrlWithSortParams(params).isEmpty() ? "?" : "&") + "status=ok&email=" + userFormDTO.getEmail();
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", "exception.user.duplicateEmail");
            return "userForm";
        }
    }

    @GetMapping(value = {"/delete/{id}"})
    public String deleteUserById(@PathVariable long id, @RequestParam Map<String, String> params) {
        userService.deleteById(id);
        return "redirect:" + WEBPAGE_URL + getUrlWithSortParams(params);
    }

    @GetMapping(value = {"/edit/{id}"})
    public String getUpdatePage(@PathVariable long id, Model model) {
        UserFormDTO userFormDTO = userService.getUserFormDTOById(id);
        model.addAttribute("userFormDTO", userFormDTO);
        model.addAttribute("isEdit", true);
        return "userForm";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String postUpdatePage(@PathVariable long id,
                                 @Valid UserFormDTO userFormDTO, BindingResult result, Model model,
                                 @RequestParam Map<String, String> params) {

        if (result.hasErrors()) {
            return logAndReturnUserForm(model, true);
        }

        super.update(userFormDTO, id);
        return "redirect:" + WEBPAGE_URL + getUrlWithSortParams(params) + "&status=ok&email=" + userFormDTO.getEmail();
    }

    private String getUrlWithSortParams(Map<String, String> params){
        StringBuilder url = new StringBuilder();
        if (params.get("page") != null) {
            url.append("?")
                    .append((params.get("page") != null ? "page=" + params.get("page") : ""))
                    .append((params.get("size") != null ? "&size=" + params.get("size") : ""))
                    .append((params.get("sort") != null ? "&sort=" + params.get("sort") : ""));
        }
        return url.toString();
    }

    private String logAndReturnUserForm(Model model, boolean isEdit){
        log.debug("User form has an errors");
        model.addAttribute("isEdit", isEdit);
        return "userForm";
    }
}

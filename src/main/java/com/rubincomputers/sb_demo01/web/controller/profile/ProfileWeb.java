package com.rubincomputers.sb_demo01.web.controller.profile;

import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ProfileWeb extends ProfileAbstract {
    static final String WEBPAGE_URL = "/profile";

    @GetMapping(value = {"", "/"})
    public String getProfile(Model model, Pageable pageable) {
        return "profile";
    }

}

package com.rubincomputers.sb_demo01.web.controller.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequestMapping(value = PostWeb.WEBPAGE_URL)
public class PostWeb extends PostAbstract {

    static final String WEBPAGE_URL = "/admin/users";




}

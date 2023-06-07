package com.rubincomputers.sb_demo01.web.controller.user;

import com.rubincomputers.sb_demo01.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class ProfileAbstract extends AbstractController {
    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id"
            ));
}

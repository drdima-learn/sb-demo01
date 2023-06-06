package com.rubincomputers.sb_demo01.web.controller.post;

import com.rubincomputers.sb_demo01.web.controller.AbstractController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractPostController extends AbstractController {
    protected static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "login",
                    "firstName",
                    "lastName",
                    "email"
            ));
}

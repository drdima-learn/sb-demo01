package com.rubincomputers.sb_demo01.web.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class AbstractController {
    protected static boolean onlyContainsAllowedProperties(Pageable pageable, List<String> allowed) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(allowed::contains);
    }
}

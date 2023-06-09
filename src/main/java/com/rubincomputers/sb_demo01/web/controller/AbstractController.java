package com.rubincomputers.sb_demo01.web.controller;

import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Slf4j
public abstract class AbstractController {
    protected void onlyAllowedSortProperties(Pageable pageable, List<String> allowed) {
        boolean result = pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(allowed::contains);
        if (!result){
            log.info("Bad Sort Parameters, pageable={}", pageable.getSort());
            throw new BadSortParameter("Bad Parameter: " + pageable.getSort());
        }
    }
}

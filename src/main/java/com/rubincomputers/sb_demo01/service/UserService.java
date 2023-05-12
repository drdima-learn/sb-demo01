package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.web.exception2.BadSortParameters;
import com.rubincomputers.sb_demo01.web.exception2.NotFoundException;
import com.rubincomputers.sb_demo01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserDTO> getAll(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            //TODO realize normal thrown
            throw new BadSortParameters("Bad Parameter: " + pageable.getSort().toString());
        }

        return userRepository.findAll(pageable).map(UserDTO::from);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {

        final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
                Arrays.asList(
                        "id",
                        "login",
                        "firstName",
                        "lastName",
                        "email"
                )
        );


        Sort sort = pageable.getSort();

        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}

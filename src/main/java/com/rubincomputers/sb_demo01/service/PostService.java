package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public Page<PostDTO> getAll(Pageable pageable) {

//            if (!onlyContainsAllowedProperties(pageable)) {
//                throw new BadSortParameter("Bad Parameter: " + pageable.getSort().toString());
//            }
//            return userRepository.findAll(pageable).map(UserMapper::dto);
        return null;
    }
}

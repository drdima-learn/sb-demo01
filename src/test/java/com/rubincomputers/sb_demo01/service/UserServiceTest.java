package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.web.data.UserTestData;
import com.rubincomputers.sb_demo01.web.exception2.BadSortParameter;
import com.rubincomputers.sb_demo01.web.exception2.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.rubincomputers.sb_demo01.web.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void getAll() {
        Page<UserDTO> all = service.getAll();
        USER_DTO_MATCHER.assertMatch(all.getContent(), allUsers);
    }

    @Test
    void getAllFirst3() {
        Page<UserDTO> first3 = service.getAll(PageRequest.of(0, 3));
        USER_DTO_MATCHER.assertMatch(first3.getContent(), user1, user2, user3);
    }

    @Test
    void getAllFirst3SortedByIdDesc() {
        Page<UserDTO> first3Desc = service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id"))));
        USER_DTO_MATCHER.assertMatch(first3Desc.getContent(), user20, user19, user18);
    }

    @Test
    void getAllBadSortParameter() {
        assertThrows(BadSortParameter.class, () -> service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id2")))));
    }

    @Test
    void get() {
        UserDTO userDTOActual = service.get(UserTestData.USER_ID);
        USER_DTO_MATCHER.assertMatch(userDTOActual, user1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_NOT_FOUND));
    }
}
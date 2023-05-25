package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.web.data.UserTestData;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.TransactionException;

import static com.rubincomputers.sb_demo01.dto.UserDTO.dto;
import static com.rubincomputers.sb_demo01.web.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void getAll() {
        Page<UserDTO> all = service.getAll();
        USER_DTO_MATCHER.assertMatch(all.getContent(), allUsersDTO);
    }

    @Test
    void getAllFirst3() {
        Page<UserDTO> first3 = service.getAll(PageRequest.of(0, 3));
        USER_DTO_MATCHER.assertMatch(first3.getContent(), dto(user1), dto(user2), dto(user3));
    }

    @Test
    void getAllFirst3SortedByIdDesc() {
        Page<UserDTO> first3Desc = service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id"))));
        USER_DTO_MATCHER.assertMatch(first3Desc.getContent(), dto(user20), dto(user19), dto(user18));
    }

    @Test
    void getAllBadSortParameter() {
        assertThrows(BadSortParameter.class, () -> service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id2")))));
    }

    @Test
    void get() {
        UserDTO userDTOActual = service.get(UserTestData.USER_ID);
        USER_DTO_MATCHER.assertMatch(userDTOActual, dto(user1));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(USER_ID_NOT_FOUND));
    }

    @Test
    void getByEmail() {
        UserDTO userDTOActual = service.getByEmail(user1.getEmail());
        USER_DTO_MATCHER.assertMatch(userDTOActual, dto(user1));
    }

    @Test
    void create() {
        User newUser = getNew();

        User created = service.create(newUser);
        Long newId = created.getId();
        newUser.setId(newId);
        UserDTO newUserDTO = UserDTO.dto(newUser);

        USER_MATCHER.assertMatch(created, newUser);
        USER_DTO_MATCHER.assertMatch(service.get(newId), newUserDTO);
    }

    @Test
    void createDuplicateEmail() {
        User newUser = getNew();
        newUser.setEmail(user1.getEmail()); //set duplicate email

        assertThrows(DataAccessException.class, () -> service.create(newUser));

    }

    @Test
    void createBlankFirstName() {
        User newUser = getNew();
        newUser.setFirstName("");

        assertThrows(TransactionException.class, () -> service.create(newUser));

    }
}
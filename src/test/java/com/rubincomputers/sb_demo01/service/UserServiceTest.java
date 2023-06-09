package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.data.UserTestData;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.transaction.TransactionException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.service.mapper.UserMapper.toDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        USER_DTO_MATCHER.assertMatch(first3.getContent(), UserMapper.toDto(user1), UserMapper.toDto(user2), UserMapper.toDto(user3));
    }

    @Test
    void getAllFirst3SortedByIdDesc() {
        Page<UserDTO> first3Desc = service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id"))));
        USER_DTO_MATCHER.assertMatch(first3Desc.getContent(), UserMapper.toDto(user20), UserMapper.toDto(user19), UserMapper.toDto(user18));
    }

    @Test
    void getAllBadSortParameter() {
        assertThrows(PropertyReferenceException.class, () -> service.getAll(PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id2")))));
    }

    @Test
    void get() {
        UserDTO userDTOActual = service.getUserDTOById(UserTestData.USER_ID);
        USER_DTO_MATCHER.assertMatch(userDTOActual, UserMapper.toDto(user1));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getUserDTOById(USER_ID_NOT_EXISTS));
    }

    @Test
    void getByEmail() {
        UserDTO userDTOActual = service.getByEmail(user1.getEmail());
        USER_DTO_MATCHER.assertMatch(userDTOActual, UserMapper.toDto(user1));
    }

    @Test
    void create() {
        User newUser = getNew();

        User created = service.create(newUser);
        Long newId = created.getId();
        newUser.setId(newId);
        UserDTO newUserDTO = UserMapper.toDto(newUser);

        USER_MATCHER.assertMatch(created, newUser);
        USER_DTO_MATCHER.assertMatch(service.getUserDTOById(newId), newUserDTO);
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

    @Test
    void deleteUserById() {
        long before = service.getAll().getTotalElements();

        service.deleteById(USER_ID);
        assertThrows(NotFoundException.class, () -> service.getUserDTOById(USER_ID));

        long after = service.getAll().getTotalElements();

        assertTrue(before - 1 == after);
    }

    @Test
    void updateWithId() {
        UserFormDTO updatedUser = UserMapper.toUserFormDTO(getUpdatedWithId());

        service.update(updatedUser);

        UserDTO actual = service.getUserDTOById(USER_ID);

        USER_DTO_MATCHER.assertMatch(actual, UserMapper.toDto(getUpdatedWithId()));
    }

    @Test
    void updateWoId() {
        UserDTO expected = service.getUserDTOById(USER_ID);

        UserFormDTO updatedUserWoId = UserMapper.toUserFormDTO(getUpdatedWoId());
        assertThrows(IllegalArgumentException.class, () -> service.update(updatedUserWoId));

        UserDTO actual = service.getUserDTOById(USER_ID);

        USER_DTO_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> service.update(null));
    }
}
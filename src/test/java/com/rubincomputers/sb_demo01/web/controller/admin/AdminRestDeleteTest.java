package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminRestDeleteTest extends AdminRestAbstract {

    @Test
    void deleteById() throws Exception {

        Page<UserDTO> usersBeforeDelete = userService.getAll();

        restTest(HttpMethod.DELETE,
                REST_URL + USER_ID,
                HttpStatus.NO_CONTENT
        );

        assertThrows(NotFoundException.class, () -> userService.getUserDTOById(USER_ID));

        Page<UserDTO> usersAfterDelete = userService.getAll();

        assertEquals(usersBeforeDelete.getTotalElements()-1, usersAfterDelete.getTotalElements());
    }

    @Test
    void deleteByIdNotExistsId() throws Exception {

        Page<UserDTO> usersBeforeDelete = userService.getAll();

        restTest(HttpMethod.DELETE,
                REST_URL + USER_ID_NOT_EXISTS,
                HttpStatus.NOT_FOUND,
                NotFoundException.class
        );

        Page<UserDTO> usersAfterDelete = userService.getAll();

        assertEquals(usersBeforeDelete.getTotalElements(), usersAfterDelete.getTotalElements());
    }

    @Test
    void deleteByEmail() throws Exception {

        Page<UserDTO> usersBeforeDelete = userService.getAll();

        restTest(HttpMethod.DELETE,
                REST_URL + "by-email?email=" + USER_EMAIL,
                HttpStatus.NO_CONTENT
        );

        assertThrows(NotFoundException.class, () -> userService.getByEmail(USER_EMAIL));

        Page<UserDTO> usersAfterDelete = userService.getAll();

        assertEquals(usersBeforeDelete.getTotalElements()-1, usersAfterDelete.getTotalElements());
    }

    @Test
    void deleteByEmailWithNotWellFormed() throws Exception {

        Page<UserDTO> usersBeforeDelete = userService.getAll();

        restTest(HttpMethod.DELETE,
                REST_URL + "by-email?email=" + USER_EMAIL_NOT_WELL_FORMED,
                HttpStatus.BAD_REQUEST,
                ConstraintViolationException.class
        );

        Page<UserDTO> usersAfterDelete = userService.getAll();

        assertEquals(usersBeforeDelete.getSize(), usersAfterDelete.getSize());
    }
}

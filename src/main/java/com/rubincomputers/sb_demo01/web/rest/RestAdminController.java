package com.rubincomputers.sb_demo01.web.rest;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rubincomputers.sb_demo01.web.rest.RestAdminController.REST_URL;

@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestAdminController {

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    private UserService userService;

    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping
    public Page<UserDTO> getUsers(Pageable pageable) {
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return userService.getAll(pageable);
    }

    @GetMapping("/list")
    public List<UserDTO> getUsersAsList(Pageable pageable) {
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return userService.getAll(pageable).getContent();
    }
}
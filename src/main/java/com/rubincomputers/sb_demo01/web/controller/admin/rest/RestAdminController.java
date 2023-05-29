package com.rubincomputers.sb_demo01.web.controller.admin.rest;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.dto.UserRegistrationDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.web.controller.admin.AbstractAdminController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.net.URI;
import java.util.List;

import static com.rubincomputers.sb_demo01.web.controller.admin.rest.RestAdminController.REST_URL;

@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RestAdminController extends AbstractAdminController {

    static final String REST_URL = "/rest/admin/users";


    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping(value = {"", "/"})
    public Page<UserDTO> getUsers(Pageable pageable) {
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return userService.getAll(pageable);
    }

    @GetMapping("/list")
    public List<UserDTO> getUsersAsList(Pageable pageable) {
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return userService.getAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        log.debug("getUserById {}", id);
        return userService.getById(id);

    }

    @GetMapping("/by-email")
    public UserDTO getUserByEmail(@RequestParam @Email String email) {
        log.debug("getUserByEmail {}", email);
        return userService.getByEmail(email);
    }

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        User created = super.create(UserRegistrationDTO.toUser(userRegistrationDTO));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        log.info("delete user id={}", id);
        userService.deleteById(id);
    }

    @DeleteMapping("/by-email")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteByEmail(@RequestParam @Email String email) {
        log.info("delete user email={}", email);
        userService.deleteByEmail(email);
    }
}

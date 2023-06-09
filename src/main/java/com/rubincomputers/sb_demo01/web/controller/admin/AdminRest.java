package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.service.PostService;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.web.controller.post.PostAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.rubincomputers.sb_demo01.web.controller.admin.AdminRest.REST_URL;

@Slf4j
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AdminRest extends AdminAbstract {

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    protected PostService postService;

    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping(value = {"", "/"})
    public Page<UserDTO> getUsers(Pageable pageable) {
        onlyAllowedSortProperties(pageable, ALLOWED_ORDERED_PROPERTIES);
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
        return userService.getUserDTOById(id);

    }

    @GetMapping("/by-email")
    public UserDTO getUserByEmail(@RequestParam @Email String email) {
        log.debug("getUserByEmail {}", email);
        return userService.getByEmail(email);
    }

    @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserFormDTO userFormDTO) {
        UserDTO created = super.create(userFormDTO);
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@Valid @RequestBody UserFormDTO userFormDTO, @PathVariable long id){
        super.update(userFormDTO, id);
    }

    @GetMapping("/{userId}/posts")
    public Page<PostDTO> getPostsByUserId(@PathVariable long userId, Pageable pageable) {
        onlyAllowedSortProperties(pageable, PostAbstract.ALLOWED_ORDERED_PROPERTIES);
        log.debug("GET REST request to {} pageable {}", REST_URL, pageable);
        return postService.getPostsByUserId(userId, pageable);
    }

}

package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.repository.UserRepository;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.util.passwordencoder.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<UserDTO> getAll() {
        return getAll(PageRequest.of(0, Integer.MAX_VALUE));
    }

    public Page<UserDTO> getAll(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            throw new BadSortParameter("Bad Parameter: " + pageable.getSort().toString());
        }
        return userRepository.findAll(pageable).map(UserDTO::dto);
    }

    public UserDTO getById(Long id) {
        return userRepository.findById(id).map(UserDTO::dto).orElseThrow(() -> new NotFoundException("user id=" + id));
    }

    public UserDTO getByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::dto).orElseThrow(() -> new NotFoundException("user email=" + email));
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


    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(prepareToSave(user));
    }

    private User prepareToSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }


    @Transactional
    public void deleteById(long id) {
        ValidationUtil.checkNotFound(userRepository.delete(id) != 0, id);
    }

    @Transactional
    public void deleteByEmail(String email) {
        ValidationUtil.checkNotFound(userRepository.deleteByEmail(email) != 0, email);
    }
}

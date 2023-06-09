package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.repository.UserRepository;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;
import com.rubincomputers.sb_demo01.util.ValidationUtil;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import com.rubincomputers.sb_demo01.util.passwordencoder.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
        return userRepository.findAll(pageable).map(UserMapper::toDto);
    }

    public UserDTO getUserDTOById(Long id) {
        return UserMapper.toDto(getEntityById(id));
    }

    public UserFormDTO getUserFormDTOById(Long id) {
        return UserMapper.toUserFormDTO(getEntityById(id));
    }

    private User getEntityById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user id=" + id));
    }

    public UserDTO getByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDto).orElseThrow(() -> new NotFoundException("user email=" + email));
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

    @Transactional
    public void update(UserFormDTO userFormDTO) {
        Assert.notNull(userFormDTO, "userFormDTO must not be null");
        Assert.notNull(userFormDTO.getId(), "userFormDTO.id must not be null");
        User user = this.getEntityById(userFormDTO.getId());
        user = UserMapper.updateFromTo(user, userFormDTO);
        prepareToSave(user);
    }


}
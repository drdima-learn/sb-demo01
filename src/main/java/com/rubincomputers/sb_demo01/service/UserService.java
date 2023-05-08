package com.rubincomputers.sb_demo01.service;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.model.Gender;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<UserDTO> getAll(){

        return userRepository.findAll().stream().map(UserDTO::from).collect(Collectors.toList());



        /*return List.of(
                User.builder().firstName("Vasya").lastName("Pupkin").email("vasya@gmail.com").password("1234")
                        .birthDay(new Date()).gender(Gender.FEMALE).build(),
                User.builder().firstName("Petya").lastName("Ivanov").email("petya@gmail.com").password("1234").build()
        ).stream().map(UserDTO::from).collect(Collectors.toList());*/
    }
}

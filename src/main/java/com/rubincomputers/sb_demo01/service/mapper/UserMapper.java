package com.rubincomputers.sb_demo01.service.mapper;

import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
    }

    public static List<UserDTO> dto(List<User> users) {
        return users.stream().map(u -> dto(u)).collect(Collectors.toList());
    }

    public static UserDTO dto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDay(user.getBirthDay())
                .gender(user.getGender() != null ? user.getGender().toString().toLowerCase() : "")
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserFormDTO ur) {
        return User.builder()
                .id(ur.getId())
                .firstName(ur.getFirstName())
                .lastName(ur.getLastName())
                .birthDay(ur.getBirthDay())
                .gender(ur.getGender())
                .email(ur.getEmail())
                .password(ur.getPassword())
                .role(ur.getRole())
                .build();
    }

    public static UserFormDTO from(User user) {
        return UserFormDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDay(user.getBirthDay())
                .gender(user.getGender())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static MultiValueMap<String, String> toMultiValueMap(UserFormDTO dto) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("firstName", dto.getFirstName());
        formData.add("lastName", dto.getLastName());
        formData.add("birthDay", dto.getBirthDay().toString());
        formData.add("gender", dto.getGender().name());
        formData.add("email", dto.getEmail());
        formData.add("password", dto.getPassword());
        formData.add("role", dto.getRole().name());
        return formData;
    }

    public static User updateFromTo(User user, UserFormDTO dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirthDay(dto.getBirthDay());
        user.setGender(dto.getGender());
        return user;
    }
}

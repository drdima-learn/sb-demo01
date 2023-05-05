package com.rubincomputers.sb_demo01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubincomputers.sb_demo01.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthDay;

    private String gender;
    private String email;

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(u -> from(u)).collect(Collectors.toList());
    }

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDay(user.getBirthDay())
                .gender(user.getGender() != null ? user.getGender().toString().toLowerCase() : "")
                .email(user.getEmail())
                .build();
    }
}

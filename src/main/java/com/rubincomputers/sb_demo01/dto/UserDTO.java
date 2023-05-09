package com.rubincomputers.sb_demo01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubincomputers.sb_demo01.model.Role;
import com.rubincomputers.sb_demo01.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO{
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthDay;

    private String gender;
    private String email;

    //User(USER_ID, "vasya@gmail.com", "{noop}password",Role.USER, "Vasya", "Pupkin",MALE, java.sql.Date.valueOf("1982-06-11"));


    public UserDTO(Long id, String email, String firstName, String lastName, String gender, Date birthDay) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
    }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(u -> from(u)).collect(Collectors.toList());
    }

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDay(user.getBirthDay())
                .gender(user.getGender() != null ? user.getGender().toString().toLowerCase() : "")
                .email(user.getEmail())
                .build();
    }
}

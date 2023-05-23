package com.rubincomputers.sb_demo01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubincomputers.sb_demo01.model.Gender;
import com.rubincomputers.sb_demo01.model.Role;
import com.rubincomputers.sb_demo01.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDTO extends BaseDTO{

    @NotBlank
    @Size(min = 2, max = 128)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 128)
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthDay;


    private Gender gender;

    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 128)

    private String password;

    private Role role;

    public static User toUser(UserRegistrationDTO ur){
        return User.builder()
                .firstName(ur.getFirstName())
                .lastName(ur.getLastName())
                .birthDay(ur.getBirthDay())
                .gender(ur.getGender())
                .email(ur.getEmail())
                .password(ur.getPassword())
                .role(ur.getRole())
                .build();

    }

    public static UserRegistrationDTO from(User user) {
        return UserRegistrationDTO.builder()
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




}

package com.rubincomputers.sb_demo01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubincomputers.sb_demo01.model.Gender;
import com.rubincomputers.sb_demo01.model.Role;
import com.rubincomputers.sb_demo01.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserFormDTO extends BaseDTO {

    @NotBlank
    @Size(min = 2, max = 128)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 128)
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthDay;

    private Gender gender;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 128)

    private String password;

    private Role role;

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
}

package com.rubincomputers.sb_demo01.service.dto;

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


}

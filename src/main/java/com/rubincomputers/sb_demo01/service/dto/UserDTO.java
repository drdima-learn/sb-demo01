package com.rubincomputers.sb_demo01.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubincomputers.sb_demo01.model.Gender;
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

    public UserDTO(Long id, String email, String firstName, String lastName, String gender, Date birthDay) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
    }




}

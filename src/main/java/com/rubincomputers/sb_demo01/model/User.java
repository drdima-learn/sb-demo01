package com.rubincomputers.sb_demo01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String email;
    private String password;
    private Role role;
}

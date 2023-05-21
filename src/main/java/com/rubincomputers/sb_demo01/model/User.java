package com.rubincomputers.sb_demo01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rc_user")
public class User extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "birth_day", nullable = false, columnDefinition = "timestamp")
    private Date birthDay;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;


    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}

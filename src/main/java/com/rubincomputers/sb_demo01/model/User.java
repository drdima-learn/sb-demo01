package com.rubincomputers.sb_demo01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rc_user")
public class User extends AbstractBaseEntity{

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "birth_day", nullable = false, columnDefinition = "timestamp")
    private Date birthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;



    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

//    public User(Long id, String email, String password, Role role, String firstName, String lastName, Gender gender, Date birthDay) {
//        this.id=id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.birthDay = birthDay;
//        this.gender = gender;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
}

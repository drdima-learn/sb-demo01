package com.rubincomputers.sb_demo01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.rubincomputers.sb_demo01.model.Gender.MALE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
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


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    public User(Long id, String firstName, String lastName, Date birthDay, Gender gender, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
        this.password = "no password";
        this.role = Role.USER;
    }

    public User(Long id, String firstName, String lastName, Date birthDay, Gender gender, String email, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //public static final User user3 = new User(USER_ID + 2, "vasya3@gmail.com", "Vasya3", "Pupkin3", MALE, date("1982-06-03"));
    public User(Long id, String email, String firstName, String lastName, Gender gender, Date birthDay, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

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
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "rc_user")
public class User extends AbstractBaseEntity{


    @Size(min = 2, max = 128)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;


    @Size(min = 2, max = 128)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "birth_day", nullable = false, columnDefinition = "timestamp")
    private Date birthDay;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Size(min = 2, max = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    public User(Long id, String firstName, String lastName, Date birthDay, Gender gender, String email) {
        this(id, firstName, lastName, birthDay, gender, email,"no password",Role.USER );
    }

    public User(Long id, String firstName, String lastName, Date birthDay, Gender gender, String email, String password, Role role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String email, String firstName, String lastName, Gender gender, Date birthDay, String password, Role role) {
        this(id, firstName, lastName, birthDay, gender, email,"no password",role );
    }
}

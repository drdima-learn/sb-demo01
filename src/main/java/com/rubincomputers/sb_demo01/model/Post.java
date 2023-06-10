package com.rubincomputers.sb_demo01.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "rc_post")
public class Post extends AbstractBaseEntity{
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Post(Long id, String text, User user) {
        super(id);
        this.text = text;
        this.user = user;
    }

    public Post(Long id, String text) {
        this(id, text, null);

    }

    public Post(String text) {
        this(null, text, null);
    }

    public Post(String text, User user) {
        this(null, text, user);
    }
}

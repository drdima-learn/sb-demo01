package com.rubincomputers.sb_demo01.data;

import com.rubincomputers.sb_demo01.model.Gender;
import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.rubincomputers.sb_demo01.data.UserTestData.*;
import static com.rubincomputers.sb_demo01.model.AbstractBaseEntity.START_SEQ;
import static com.rubincomputers.sb_demo01.model.Gender.MALE;
import static com.rubincomputers.sb_demo01.model.Role.ADMIN;
import static com.rubincomputers.sb_demo01.model.Role.USER;

public class PostTestData {
    public static final MatcherFactory.Matcher<PostDTO> POST_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(PostDTO.class);
    public static final MatcherFactory.Matcher<Post> POST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Post.class);

    public static final long POST_ID = START_SEQ+20;

    public static final Post post1 = new Post(POST_ID, "post1 100000", user1);
    public static final Post post2 = new Post(POST_ID+1, "post2 100000", user1);
    public static final Post post3 = new Post(POST_ID+2, "post3 100000", user1);

    public static final Post post4 = new Post(POST_ID+3, "post1 100001", user2);
    public static final Post post5 = new Post(POST_ID+4, "post2 100001", user2);
    public static final Post post6 = new Post(POST_ID+5, "post3 100001", user2);




    public static Post getNew() {
        return Post.builder()
                .text("new post from test")
                .build();
    }
//
//    public static User getUpdatedWoId() {
//        return User.builder()
//                .firstName("updated FirstName")
//                .lastName("updated LastName")
//                .birthDay(date("2022-01-01"))
//                .gender(Gender.FEMALE)
//                .role(ADMIN)
//                .email("updated@gmail.com")
//                .password("updated pass")
//                .build();
//    }
//
//    public static User getUpdatedWithId() {
//        User user = getUpdatedWoId();
//        user.setId(USER_ID);
//        return user;
//    }
}

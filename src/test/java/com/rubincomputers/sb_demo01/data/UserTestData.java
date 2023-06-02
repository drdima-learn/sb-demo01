package com.rubincomputers.sb_demo01.data;

import com.rubincomputers.sb_demo01.model.Gender;
import com.rubincomputers.sb_demo01.model.User;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.mapper.UserMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.rubincomputers.sb_demo01.model.AbstractBaseEntity.START_SEQ;
import static com.rubincomputers.sb_demo01.model.Gender.MALE;
import static com.rubincomputers.sb_demo01.model.Role.ADMIN;
import static com.rubincomputers.sb_demo01.model.Role.USER;

public class UserTestData {
    public static final MatcherFactory.Matcher<UserDTO> USER_DTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDTO.class, "birthDay");
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "birthDay", "password");

    public static final long USER_ID = START_SEQ;
    public static final long USER_ID_NOT_EXISTS = 123L;

    public static final String USER_EMAIL = "vasya@gmail.com";
    public static final String USER_EMAIL_NOT_EXISTS = "notexists@gmail.com";
    public static final String USER_EMAIL_NOT_WELL_FORMED = "notwellformedgmail.com";


    public static final User user1 = new User(USER_ID, "Вася", "Пупкин", date("1982-06-11"), MALE, "vasya@gmail.com", "password", USER);
    public static final User user2 = new User(USER_ID + 1, "Vasya2", "Pupkin2", date("1982-06-02"), MALE, "vasya2@gmail.com", "password", USER);
    public static final User user3 = new User(USER_ID + 2, "vasya3@gmail.com", "Vasya3", "Pupkin3", MALE, date("1982-06-03"), "password", USER);
    public static final User user4 = new User(USER_ID + 3, "vasya4@gmail.com", "Vasya4", "Pupkin4", MALE, date("1982-06-04"), "password", USER);
    public static final User user5 = new User(USER_ID + 4, "vasya5@gmail.com", "Vasya5", "Pupkin5", MALE, date("1982-06-05"), "password", USER);
    public static final User user6 = new User(USER_ID + 5, "vasya6@gmail.com", "Vasya6", "Pupkin6", MALE, date("1982-06-06"), "password", USER);
    public static final User user7 = new User(USER_ID + 6, "vasya7@gmail.com", "Vasya7", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user8 = new User(USER_ID + 7, "vasya8@gmail.com", "Vasya8", "Pupkin8", MALE, date("1982-06-08"), "password", USER);
    public static final User user9 = new User(USER_ID + 8, "vasya9@gmail.com", "Vasya9", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user10 = new User(USER_ID + 9, "vasya10@gmail.com", "Vasya10", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user11 = new User(USER_ID + 10, "vasya11@gmail.com", "Vasya11", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user12 = new User(USER_ID + 11, "vasya12@gmail.com", "Vasya12", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user13 = new User(USER_ID + 12, "vasya13@gmail.com", "Vasya13", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user14 = new User(USER_ID + 13, "vasya14@gmail.com", "Vasya14", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user15 = new User(USER_ID + 14, "vasya15@gmail.com", "Vasya15", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user16 = new User(USER_ID + 15, "vasya16@gmail.com", "Vasya16", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user17 = new User(USER_ID + 16, "vasya17@gmail.com", "Vasya17", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user18 = new User(USER_ID + 17, "vasya18@gmail.com", "Vasya18", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user19 = new User(USER_ID + 18, "vasya19@gmail.com", "Vasya19", "Pupkin7", MALE, date("1982-06-07"), "password", USER);
    public static final User user20 = new User(USER_ID + 19, "vasya20@gmail.com", "Vasya20", "Pupkin7", MALE, date("1982-06-07"), "password", USER);

    public static final List<User> allUsersEntity = List.of(user1, user2, user3, user4, user5,
            user6, user7, user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18, user19, user20);
    public static final List<UserDTO> allUsersDTO = UserMapper.dto(allUsersEntity);

    private static Date date(String dateString) {
        //String dateString = "2022-05-10"; // Example date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;

    }

    public static User getNew() {
        return User.builder()
                .firstName("New FirstName")
                .lastName("New LastName")
                .birthDay(date("2022-01-01"))
                .gender(Gender.FEMALE)
                .role(USER)
                .email("new@gmail.com")
                .password("new4321")
                .build();
    }

    public static User getUpdateWoId() {
        return User.builder()
                .firstName("updated FirstName")
                .lastName("updated LastName")
                .birthDay(date("2022-01-01"))
                .gender(Gender.FEMALE)
                .role(ADMIN)
                .email("updated@gmail.com")
                .password("updated pass")
                .build();
    }
}

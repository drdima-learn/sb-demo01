package com.rubincomputers.sb_demo01.controller.data;

import com.rubincomputers.sb_demo01.dto.UserDTO;
import com.rubincomputers.sb_demo01.model.Gender;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.rubincomputers.sb_demo01.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<UserDTO> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserDTO.class, "birthDay");

    public static final long USER_ID = START_SEQ;

    private static final String MALE = Gender.MALE.name().toLowerCase();


    public static final UserDTO user1 = new UserDTO(USER_ID, "vasya@gmail.com", "Vasya", "Pupkin", MALE, date("1982-06-11"));
    public static final UserDTO user2 = new UserDTO(USER_ID + 1, "vasya2@gmail.com", "Vasya2", "Pupkin2", MALE, date("1982-06-02"));
    public static final UserDTO user3 = new UserDTO(USER_ID + 2, "vasya3@gmail.com", "Vasya3", "Pupkin3", MALE, date("1982-06-03"));
    public static final UserDTO user4 = new UserDTO(USER_ID + 3, "vasya4@gmail.com", "Vasya4", "Pupkin4", MALE, date("1982-06-04"));
    public static final UserDTO user5 = new UserDTO(USER_ID + 4, "vasya5@gmail.com", "Vasya5", "Pupkin5", MALE, date("1982-06-05"));
    public static final UserDTO user6 = new UserDTO(USER_ID + 5, "vasya6@gmail.com", "Vasya6", "Pupkin6", MALE, date("1982-06-06"));
    public static final UserDTO user7 = new UserDTO(USER_ID + 6, "vasya7@gmail.com", "Vasya7", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user8 = new UserDTO(USER_ID + 7, "vasya8@gmail.com", "Vasya8", "Pupkin8", MALE, date("1982-06-08"));
    public static final UserDTO user9 = new UserDTO(USER_ID + 8, "vasya9@gmail.com", "Vasya9", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user10 = new UserDTO(USER_ID + 9, "vasya10@gmail.com", "Vasya10", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user11 = new UserDTO(USER_ID + 10, "vasya11@gmail.com", "Vasya11", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user12 = new UserDTO(USER_ID + 11, "vasya12@gmail.com", "Vasya12", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user13 = new UserDTO(USER_ID + 12, "vasya13@gmail.com", "Vasya13", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user14 = new UserDTO(USER_ID + 13, "vasya14@gmail.com", "Vasya14", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user15 = new UserDTO(USER_ID + 14, "vasya15@gmail.com", "Vasya15", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user16 = new UserDTO(USER_ID + 15, "vasya16@gmail.com", "Vasya16", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user17 = new UserDTO(USER_ID + 16, "vasya17@gmail.com", "Vasya17", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user18 = new UserDTO(USER_ID + 17, "vasya18@gmail.com", "Vasya18", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user19 = new UserDTO(USER_ID + 18, "vasya19@gmail.com", "Vasya19", "Pupkin7", MALE, date("1982-06-07"));
    public static final UserDTO user20 = new UserDTO(USER_ID + 19, "vasya20@gmail.com", "Vasya20", "Pupkin7", MALE, date("1982-06-07"));

    public static final List<UserDTO> allUsers = List.of(user1, user2, user3, user4, user5,
            user6, user7, user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18, user19, user20);


    private static Date date(String dateString) {
        //String dateString = "2022-05-10"; // Example date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;

    }


}

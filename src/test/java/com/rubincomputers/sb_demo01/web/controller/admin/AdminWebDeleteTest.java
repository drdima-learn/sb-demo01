package com.rubincomputers.sb_demo01.web.controller.admin;

import com.rubincomputers.sb_demo01.data.UserTestData;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminWebDeleteTest extends AdminWebAbstract {

    @Test
    void deleteUserById() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/delete/" + UserTestData.USER_ID,
                HttpStatus.FOUND, //301 redirect
                header().string("Location", containsString(AdminWeb.WEBPAGE_URL))
        );
    }

    @Test
    void deleteUserByIdNotExists() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/delete/" + UserTestData.USER_ID_NOT_EXISTS,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                NotFoundException.class
        );
    }

    @Test
    void deleteUserByIdAsString() throws Exception {
        pageTest(HttpMethod.GET,
                WEBPAGE_URL + "/delete/" + UserTestData.USER_ID_NOT_EXISTS + 'O', //as O letter
                HttpStatus.INTERNAL_SERVER_ERROR,
                "exception",
                MethodArgumentTypeMismatchException.class
        );
    }


}

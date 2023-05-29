package com.rubincomputers.sb_demo01.web;

import com.rubincomputers.sb_demo01.web.json.JsonUtil;
import com.rubincomputers.sb_demo01.web.util.mockmvc.TrueResultMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import static com.rubincomputers.sb_demo01.data.UserTestData.USER_DTO_MATCHER;
import static com.rubincomputers.sb_demo01.data.UserTestData.user1;
import static com.rubincomputers.sb_demo01.dto.UserDTO.dto;
import static com.rubincomputers.sb_demo01.web.util.mockmvc.HttpMethodToMockMvcRequestBuilders.convert;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "classpath:config/dev/db/data-dev.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AbstractControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    //get
    protected ResultActions restTest(HttpMethod method, String url, HttpStatus status, ResultMatcher result) throws Exception {

        return restTest(method, url, "", status, result);
    }

    //post
    protected ResultActions restTest(HttpMethod method, String url, String sendContent, HttpStatus status) throws Exception {

        return restTest(method, url, sendContent, status, TrueResultMatcher.alwaysTrue());
    }

    protected ResultActions restTest(HttpMethod method, String url, HttpStatus status) throws Exception {

        return restTest(method, url, "", status, TrueResultMatcher.alwaysTrue());
    }

    protected ResultActions restTest(HttpMethod method, String url, String sendContent, HttpStatus status, ResultMatcher result) throws Exception {

         ResultActions resultAction = mockMvc.perform(convert(method).method(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sendContent)
                )
                .andExpect(status().is(status.value()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(result);

         if (!(result instanceof TrueResultMatcher)) {
             resultAction.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
         }
         return resultAction;
    }

}

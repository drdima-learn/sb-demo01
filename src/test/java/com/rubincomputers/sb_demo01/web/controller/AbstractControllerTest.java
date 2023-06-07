package com.rubincomputers.sb_demo01.web.controller;

import com.rubincomputers.sb_demo01.AbstractTest;
import com.rubincomputers.sb_demo01.web.util.mockmvc.TrueResultMatcher;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public abstract class AbstractControllerTest extends AbstractTest {

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


    protected ResultMatcher expectRestException(Class<? extends Throwable> ex) {
        return jsonPath("$.exception", containsString(ex.getSimpleName()));
    }

    //get rest
    protected ResultActions restTest(HttpMethod method, String url, HttpStatus status, ResultMatcher matcher) throws Exception {
        return requestTest(method, url, "", null, status, "", new ResultMatcher[]{matcher});
    }

    protected ResultActions restTest(HttpMethod method, String url, HttpStatus status, Class<? extends Throwable> ex) throws Exception {
        return requestTest(method, url, "", null, status, "", new ResultMatcher[]{expectRestException(ex)});
    }

    //post rest , expect only status like 201 CREATED
    protected ResultActions restTest(HttpMethod method, String url, String sendContent, HttpStatus status) throws Exception {
        return requestTest(method, url, sendContent, null, status, "", new ResultMatcher[]{TrueResultMatcher.alwaysTrue()});
    }

    //post rest , expect status like 400, and matcher like exception
    protected ResultActions restTest(HttpMethod method, String url, String sendContent, HttpStatus status, ResultMatcher matcher) throws Exception {
        return requestTest(method, url, sendContent, null, status, "", new ResultMatcher[]{matcher});
    }

    protected ResultActions restTest(HttpMethod method, String url, String sendContent, HttpStatus status, Class<? extends Throwable> ex) throws Exception {
        return requestTest(method, url, sendContent, null, status, "", new ResultMatcher[]{expectRestException(ex)});
    }

    //delete rest
    protected ResultActions restTest(HttpMethod method, String url, HttpStatus status) throws Exception {
        return requestTest(method, url, "", null, status, "", new ResultMatcher[]{TrueResultMatcher.alwaysTrue()});
    }

    //webpage get request
    protected ResultActions pageTest(HttpMethod method, String url, HttpStatus status, String viewName, ResultMatcher... matchers) throws Exception {
        List<ResultMatcher> matcherList = new ArrayList<>(List.of(matchers));
        matcherList.add(
                content().contentTypeCompatibleWith(MediaType.TEXT_HTML)
        );
        return requestTest(method, url, "", null, status, viewName, matcherList.toArray(new ResultMatcher[0]));
    }

    //webpage get with exception
    protected ResultActions pageTest(HttpMethod method, String url, HttpStatus status, String viewName, Class<? extends Throwable> ex) throws Exception {
        ResultMatcher[] matchers = new ResultMatcher[]{
                content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                content().string(containsString(ex.getSimpleName()))
        };
        return requestTest(method, url, "", null, status, viewName, matchers);
    }

    //webpage get with viewName and contains string
    protected ResultActions pageTest(HttpMethod method, String url, HttpStatus status, String viewName, String containsString) throws Exception {
        ResultMatcher[] matchers = new ResultMatcher[]{
                content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                content().string(containsString(containsString))
        };
        return requestTest(method, url, "", null, status, viewName, matchers);
    }

    //webpage post with form data
    protected ResultActions pageTest(HttpMethod method, String url, MultiValueMap<String, String> sendFormData,
                                     HttpStatus status, String viewName, String containsString) throws Exception {
        ResultMatcher[] matchers = new ResultMatcher[]{
                content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                content().string(containsString(containsString))
        };
        return requestTest(method, url, "", sendFormData, status, viewName, matchers);
    }

    //webpage post with form data, and expect response redirection
    protected ResultActions pageTest(HttpMethod method, String url, MultiValueMap<String, String> sendFormData,
                                     HttpStatus status, ResultMatcher... matchers) throws Exception {

        ResultMatcher[] internalMatchers = new ResultMatcher[]{
                //content().contentTypeCompatibleWith(MediaType.TEXT_HTML)
        };
        ResultMatcher[] combinedMatchers = ArrayUtils.addAll(internalMatchers, matchers);
        return requestTest(method, url, "", sendFormData, status, "", combinedMatchers);
    }

    //webpage get and expect response redirection
    protected ResultActions pageTest(HttpMethod method, String url, HttpStatus status, ResultMatcher... matchers) throws Exception {

        ResultMatcher[] internalMatchers = new ResultMatcher[]{
                //content().contentTypeCompatibleWith(MediaType.TEXT_HTML)
        };
        ResultMatcher[] combinedMatchers = ArrayUtils.addAll(internalMatchers, matchers);
        return requestTest(method, url, "", null, status, "", combinedMatchers);
    }



    protected ResultActions cssTest(HttpMethod method, String url, HttpStatus status) throws Exception {
        ResultMatcher[] matchers = new ResultMatcher[]{
                content().contentTypeCompatibleWith(MediaType.valueOf("text/css")),
        };
        return requestTest(method, url, "", null, status, "", matchers);
    }


    private ResultActions requestTest(HttpMethod method, String url, String sendContent, MultiValueMap<String, String> formData, HttpStatus status, String viewName, ResultMatcher[] matchers) throws Exception {

        MediaType sendMediaType = MediaType.APPLICATION_JSON;
        if (formData == null) {
            formData = new LinkedMultiValueMap<>();
        } else {
            sendMediaType = MediaType.APPLICATION_FORM_URLENCODED;
        }


        //MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request(method, url);


        ResultActions resultAction = mockMvc.perform(
                MockMvcRequestBuilders.request(method, url)
                        .contentType(sendMediaType)
                        .params(formData)
                        .content(sendContent)
        );

        if (viewName != null && !viewName.isBlank()) {
            resultAction.andExpect(view().name(viewName));
        }

        resultAction
                .andExpect(status().is(status.value()))
                .andDo(MockMvcResultHandlers.print());

        for (ResultMatcher matcher : matchers) {
            resultAction.andExpect(matcher);
        }

        return resultAction;
    }


}

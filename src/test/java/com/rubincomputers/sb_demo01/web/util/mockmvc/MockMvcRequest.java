package com.rubincomputers.sb_demo01.web.util.mockmvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@FunctionalInterface
public interface MockMvcRequest {
    MockHttpServletRequestBuilder method(String url);
}

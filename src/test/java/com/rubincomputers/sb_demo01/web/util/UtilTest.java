package com.rubincomputers.sb_demo01.web.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {

    @Test
    void getFullUrlWithoutQueryString() {
        // Create a mock HttpServletRequest object
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);


        String originalUrl = "http://example.com/path";
        // Set the desired URL using a stubbed method invocation
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(originalUrl));

        String fullUrl = Util.getFullUrl(request);
        assertEquals(originalUrl, fullUrl);
    }


    @Test
    void getFullUrlWithQueryString() {
        // Create a mock HttpServletRequest object
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String originalUrl = "http://example.com/path?a=1&b=2";
        // Set the desired URL using a stubbed method invocation
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(originalUrl));

        String fullUrl = Util.getFullUrl(request);
        assertEquals(originalUrl, fullUrl);
    }
}
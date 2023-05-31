package com.rubincomputers.sb_demo01.web.util.mockmvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

public class TrueResultMatcher implements ResultMatcher {

    @Override
    public void match(MvcResult result) throws Exception {
        // No comparison is performed
    }

    public static TrueResultMatcher alwaysTrue() {
        return new TrueResultMatcher();
    }
}

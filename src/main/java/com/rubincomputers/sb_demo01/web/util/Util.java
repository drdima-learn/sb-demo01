package com.rubincomputers.sb_demo01.web.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

    private Util() {
    }

    public static String getFullUrl(HttpServletRequest req){
        String fullUrl = req.getRequestURL().toString();

        String queryString = req.getQueryString();
        if (queryString != null) {
            fullUrl += "?" + queryString;
        }
        return fullUrl;
    }
}

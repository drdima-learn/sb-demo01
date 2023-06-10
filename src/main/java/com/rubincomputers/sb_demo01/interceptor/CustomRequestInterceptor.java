package com.rubincomputers.sb_demo01.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Slf4j
@Component
public class CustomRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = Instant.now().toEpochMilli();
        //log.info("Request URL :: {} :: Start Time={}" , request.getRequestURL() , Instant.now().toString());
        log.info("Request URL {} :: {}" , request.getMethod(),  request.getRequestURL());

        //request.setAttribute("startTime", startTime);
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //long startTime = (Long) request.getAttribute("startTime");

        //log.info("Request URL::" + request.getRequestURL().toString() +
        //        ":: Time Taken=" + (Instant.now().toEpochMilli() - startTime));
    }

}

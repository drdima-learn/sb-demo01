package com.rubincomputers.sb_demo01.config;

import com.rubincomputers.sb_demo01.interceptor.CustomRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestAppConfig  implements WebMvcConfigurer {

    @Autowired
    private CustomRequestInterceptor customRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor)
        //        .addPathPatterns("/**/log-incoming-request/**/")
                .addPathPatterns("/**")
        ;
    }
}

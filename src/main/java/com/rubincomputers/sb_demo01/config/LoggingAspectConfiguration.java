package com.rubincomputers.sb_demo01.config;

import com.rubincomputers.sb_demo01.aop.logging.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(SpringProfiles.DEV)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}

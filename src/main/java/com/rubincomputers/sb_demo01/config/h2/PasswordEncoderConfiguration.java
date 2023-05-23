package com.rubincomputers.sb_demo01.config.h2;

import com.rubincomputers.sb_demo01.util.passwordencoder.PasswordEncoder;
import com.rubincomputers.sb_demo01.util.passwordencoder.PasswordEncoderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new PasswordEncoderImpl();
    }
}

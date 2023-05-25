package com.rubincomputers.sb_demo01.util.passwordencoder;

public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return rawPassword;
    }
}

package com.rubincomputers.sb_demo01.config;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Spring Boot provides a convenient mechanism for converting values to enumerations, even when the values are in lowercase.
 * // https://www.baeldung.com/spring-boot-enum-mapping
 * this works for @ModelAttribute, and @RequestParam , but not in @RequestBody
 * for @RequestBody, create a method in enum
 *
 *     @JsonCreator
 *     public static Gender fromText(String text){
 *        return Gender.valueOf(text.toUpperCase());
 *     }
 *
 */

@Configuration
public class EnumMappingAppConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
    }
}

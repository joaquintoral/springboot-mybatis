package com.bkjeon.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Log4j2
public class WebConfig implements WebMvcConfigurer {
    final String className = this.getClass().getSimpleName();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("MCI > " + className + " -> addResourceHandlers()");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        log.info("MCO > " + className + " -> addResourceHandlers()");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("MCI > " + className + " -> passwordEncoder()");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("MCO > " + className + " -> passwordEncoder()");
        return bCryptPasswordEncoder;
    }
}

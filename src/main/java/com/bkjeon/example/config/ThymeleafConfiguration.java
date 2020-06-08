package com.bkjeon.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@Log4j2
public class ThymeleafConfiguration {
    final String className = this.getClass().getSimpleName();

    @Bean
    public SpringTemplateEngine templateEngine() {
        log.info("MCI > " + className + " -> templateEngine()");
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        log.info("MCO > " + className + " -> templateEngine()");
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        log.info("MCI > " + className + " -> thymeleafTemplateResolver()");
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        log.info("MCO > " + className + " -> thymeleafTemplateResolver()");
        return templateResolver;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        log.info("MCI > " + className + " -> thymeleafViewResolver()");
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        log.info("MCO > " + className + " -> thymeleafViewResolver()");
        return viewResolver;
    }

}

package com.bkjeon.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Log4j2
public class ExampleApplication {
    final static String className = ExampleApplication.class.getSimpleName();

    public static void main(String[] args) {
        log.info("MCI > " + className + " -> main()");
        SpringApplication.run(ExampleApplication.class, args);
        log.info("MCO > " + className + " -> main()");
    }

}


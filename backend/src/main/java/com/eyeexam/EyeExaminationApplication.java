package com.eyeexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 耳鼻咽喉科检查系统主应用类
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.eyeexam")
public class EyeExaminationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EyeExaminationApplication.class, args);
    }
} 
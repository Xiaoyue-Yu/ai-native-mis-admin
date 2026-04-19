package com.creator.mis.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.creator.mis")
@MapperScan("com.creator.mis.system.mapper")
public class MisSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MisSystemApplication.class, args);
    }
}

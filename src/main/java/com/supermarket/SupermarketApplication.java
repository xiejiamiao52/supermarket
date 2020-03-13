package com.supermarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
@MapperScan("com.supermarket.Repository")
public class SupermarketApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);
    }

}

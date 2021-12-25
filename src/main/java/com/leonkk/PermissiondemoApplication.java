package com.leonkk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.leonkk.dao")
@EnableTransactionManagement  //开启事务
public class PermissiondemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissiondemoApplication.class, args);
    }

}

package com.tlg.bkwb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.tlg.bkwb.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class BkwbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BkwbApplication.class, args);

    }

}

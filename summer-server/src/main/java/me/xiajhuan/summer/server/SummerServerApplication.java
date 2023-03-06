package me.xiajhuan.summer.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("me.xiajhuan.summer")
@MapperScan("me.xiajhuan.summer.**.mapper")
public class SummerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummerServerApplication.class, args);
    }

}

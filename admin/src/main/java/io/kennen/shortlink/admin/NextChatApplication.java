package io.kennen.shortlink.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.kennen.shortlink.admin.dao.mapper")
@SpringBootApplication
public class NextChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(NextChatApplication.class, args);
    }
}

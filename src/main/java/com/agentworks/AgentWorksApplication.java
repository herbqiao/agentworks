package com.agentworks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.agentworks.mapper")
@SpringBootApplication
public class AgentWorksApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentWorksApplication.class, args);
    }
}

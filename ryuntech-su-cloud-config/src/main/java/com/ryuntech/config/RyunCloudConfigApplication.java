package com.ryuntech.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 分布式配置中心  Config
 *
 * @author tycoyyding
 * @date 2019-05-21
 */
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RyunCloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(RyunCloudConfigApplication.class, args);
    }
}

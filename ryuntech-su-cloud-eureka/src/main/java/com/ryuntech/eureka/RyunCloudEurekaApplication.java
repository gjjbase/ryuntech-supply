package com.ryuntech.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心 Eureka
 *
 * @author antu
 * @date 2019-05-21
 */
@EnableEurekaServer
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RyunCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RyunCloudEurekaApplication.class, args);
    }
}

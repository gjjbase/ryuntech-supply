package com.ryuntech.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 身份校验 auth
 *
 * @author antu
 * @date 2019-05-21
 */
@EnableFeignClients("com.ryuntech.admin.api.feign")
@EnableEurekaClient
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

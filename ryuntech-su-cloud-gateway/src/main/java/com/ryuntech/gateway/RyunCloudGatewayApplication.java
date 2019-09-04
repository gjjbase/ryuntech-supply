package com.ryuntech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * API网关 Zuul
 *
 * @author antu
 * @date 2019-05-21
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RyunCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RyunCloudGatewayApplication.class, args);
    }
}

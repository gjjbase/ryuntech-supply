package com.ryuntech.saas;

import com.ryuntech.saas.api.helper.EnableRyunResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author EDZ
 */
@EnableRyunResourceServer
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class RyunCloudServiceSaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RyunCloudServiceSaasApplication.class, args);
    }
}

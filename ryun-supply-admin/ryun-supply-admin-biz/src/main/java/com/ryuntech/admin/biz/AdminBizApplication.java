package com.ryuntech.admin.biz;

import com.ryuntech.admin.api.annotation.EnableRyunResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统API业务接口 admin
 *
 * @author antu
 * @date 2019-05-22
 */
@EnableRyunResourceServer
@MapperScan("com.ryuntech.admin.biz.mapper")
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class AdminBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBizApplication.class, args);
    }
}

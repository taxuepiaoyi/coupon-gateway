package com.bruce.coupon.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bruce"})
public class CouponGatewayClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponGatewayClientApplication.class, args);
    }

}

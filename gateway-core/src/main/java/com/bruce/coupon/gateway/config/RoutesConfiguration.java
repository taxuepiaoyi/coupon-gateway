package com.bruce.coupon.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator declare(RouteLocatorBuilder builder){
        return builder.routes()
                .route(route -> route.path("/gateway/coupon-customer/**").filters(filter -> filter.stripPrefix(1)).uri("lb://coupon-customer"))
                .route(route -> route.order(1).path("/gateway/coupon-template").filters(filter -> filter.stripPrefix(1).removeRequestParameter("templateHels").addRequestParameter("parameter","89934")).uri("lb://coupon-template"))
                .route(route -> route.path("/gateway/coupon-calculation/**").filters(filter -> filter.stripPrefix(1)).uri("lb://coupon-calculation"))
                .build();
    }
}

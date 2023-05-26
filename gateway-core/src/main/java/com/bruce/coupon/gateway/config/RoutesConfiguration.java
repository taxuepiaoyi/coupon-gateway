package com.bruce.coupon.gateway.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@Slf4j
public class RoutesConfiguration {

    @Autowired
    private KeyResolver hostAddrKeyResolver;
    @Autowired
    @Qualifier("customerRateLimiter")
    private RateLimiter customerRateLimiter;
    @Autowired
    @Qualifier("tempalteRateLimiter")
    private RateLimiter templateRateLimiter;

    @Bean
    public RouteLocator declare(RouteLocatorBuilder builder){
        log.info("declare.......builder = {}", JSONObject.toJSONString(builder));
        return builder.routes()
                .route(route -> route.path("/gateway/customer/**").filters(filter -> filter.stripPrefix(1)).uri("lb://customer"))
                .route(route -> route.order(1).path("/gateway/template")
                        .filters(filter -> filter.stripPrefix(1).requestRateLimiter(limiter -> limiter.setRateLimiter(customerRateLimiter).setKeyResolver(hostAddrKeyResolver).setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)).removeRequestParameter("templateHels")
                                .addRequestParameter("parameter","89934")).uri("lb://template"))
                .route(route -> route.path("/gateway/calculation/**").filters(filter -> filter.stripPrefix(1)).uri("lb://coupon-calculation"))
                .build();
    }
}

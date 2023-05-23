package com.bruce.coupon.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RedisLimitationConfig {

    // 限流的维度
    @Bean
    @Primary
    public KeyResolver remoteHostLimitationKey()
    {
        return exchange -> Mono.just( exchange.getRequest() .getRemoteAddress() .getAddress() .getHostAddress() );
    }
}

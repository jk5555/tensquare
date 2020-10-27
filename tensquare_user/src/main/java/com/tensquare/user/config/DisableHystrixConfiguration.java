package com.tensquare.user.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 创建一个不初始化hystrix的feign客户端（相当于此feign客户端禁用hystrix，但是其他 的feign则不受影响）
 * @author jkun
 */
@Configuration
public class DisableHystrixConfiguration {


    @Bean
    @Scope("prototype")
    public Feign.Builder createFeignClient(){
        return Feign.builder();
    }


}

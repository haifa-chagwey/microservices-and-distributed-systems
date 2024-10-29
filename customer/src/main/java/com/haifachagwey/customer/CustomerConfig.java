package com.haifachagwey.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    @Bean
//    you instruct Spring Cloud to look up the target service's instances from a service registry (like Eureka)
//    or a configured list
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

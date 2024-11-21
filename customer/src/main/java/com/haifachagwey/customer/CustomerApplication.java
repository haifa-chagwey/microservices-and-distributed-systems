package com.haifachagwey.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

// Finding beans that are located outside the default package structure of your main application class.
@SpringBootApplication(scanBasePackages = {
        "com.haifachagwey.customer",
        "com.haifachagwey.amqp"
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.haifachagwey.clients")
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomerApplication {



    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
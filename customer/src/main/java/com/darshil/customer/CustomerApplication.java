package com.darshil.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.darshil.customer",
                "com.darshil.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.darshil.clients" // since the clients live in a different package
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
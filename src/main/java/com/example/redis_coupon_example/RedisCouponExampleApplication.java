package com.example.redis_coupon_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedisCouponExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCouponExampleApplication.class, args);
    }

}

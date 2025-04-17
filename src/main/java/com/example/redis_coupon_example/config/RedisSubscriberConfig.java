package com.example.redis_coupon_example.config;

import com.example.redis_coupon_example.service.CouponIssueWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisSubscriberConfig {

    private final CouponIssueWorker couponIssueWorker;

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(couponIssueWorker, "onIssueMessage");
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new ChannelTopic("coupon:channel"));
        return container;
    }
}

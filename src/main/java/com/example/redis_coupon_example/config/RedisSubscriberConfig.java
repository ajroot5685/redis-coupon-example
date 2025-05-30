package com.example.redis_coupon_example.config;

import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_METHOD_NAME;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_1;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_2;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_3;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_4;

import com.example.redis_coupon_example.service.redis.worker.CouponIssueWorker;
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
    public MessageListenerAdapter listener1() {
        return new MessageListenerAdapter(couponIssueWorker, WORKER_METHOD_NAME);
    }

    @Bean
    public MessageListenerAdapter listener2() {
        return new MessageListenerAdapter(couponIssueWorker, WORKER_METHOD_NAME);
    }

    @Bean
    public MessageListenerAdapter listener3() {
        return new MessageListenerAdapter(couponIssueWorker, WORKER_METHOD_NAME);
    }

    @Bean
    public MessageListenerAdapter listener4() {
        return new MessageListenerAdapter(couponIssueWorker, WORKER_METHOD_NAME);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listener1(), new ChannelTopic(WORKER_TOPIC_1));
        container.addMessageListener(listener2(), new ChannelTopic(WORKER_TOPIC_2));
        container.addMessageListener(listener3(), new ChannelTopic(WORKER_TOPIC_3));
        container.addMessageListener(listener4(), new ChannelTopic(WORKER_TOPIC_4));
        return container;
    }
}

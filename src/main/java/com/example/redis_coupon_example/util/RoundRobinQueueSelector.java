package com.example.redis_coupon_example.util;

import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_1;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_2;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_3;
import static com.example.redis_coupon_example.constant.RedisConstants.WORKER_TOPIC_4;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class RoundRobinQueueSelector {

    private final List<String> queues = List.of(
            WORKER_TOPIC_1,
            WORKER_TOPIC_2,
            WORKER_TOPIC_3,
            WORKER_TOPIC_4
    );

    private final AtomicInteger index = new AtomicInteger(0);

    public String nextWorkerTopic() {
        int i = index.getAndUpdate(curr -> (curr + 1) % queues.size());
        return queues.get(i);
    }
}

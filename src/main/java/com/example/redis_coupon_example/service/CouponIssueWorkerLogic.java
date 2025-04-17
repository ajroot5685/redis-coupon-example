package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponIssueWorkerLogic {

    private final RedisTemplate<String, CouponIssue> redisTemplate;
    private final CouponLogService couponLogService;
    private final CouponRepository couponRepository;

    //    @Async("couponWorker")
    @Transactional
    public void logic() {
//        log.info("!@");
//        log.info("작동 중인 스레드: {}", Thread.currentThread().getName());
        CouponIssue couponIssue = redisTemplate.opsForList().rightPop("coupon:queue");
        if (couponIssue != null) {
            couponRepository.findByIdWithLock(couponIssue.couponId())
                    .ifPresentOrElse(
                            coupon -> {
                                coupon.issue();
                                couponLogService.successIssue(coupon, couponIssue);
                            },
                            () -> couponLogService.failIssue(null, couponIssue)
                    );
        }
    }
}

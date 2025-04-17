package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.repository.CouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponIssueWorker {

    private final RedisTemplate<String, CouponIssue> redisTemplate;
    private final CouponLogService couponLogService;
    private final CouponRepository couponRepository;

    public void onIssueMessage(String message) {
        CouponIssue couponIssue = redisTemplate.opsForList().rightPop("coupon:queue");
        if (couponIssue != null) {
            Optional<Coupon> couponOptional = couponRepository.findById(couponIssue.couponId());

            if (couponOptional.isPresent()) {
                couponLogService.successIssue(couponOptional.get(), couponIssue);
                return;
            }

            // FIXME - 실패 로그가 중복 기록됨
            couponLogService.failIssue(null, couponIssue);
        }
    }
}

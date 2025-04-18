package com.example.redis_coupon_example.service.redis.worker;

import static com.example.redis_coupon_example.constant.RedisConstants.COUPON_DATA_QUEUE;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.service.CouponLogService;
import com.example.redis_coupon_example.service.CouponReadService;
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
    private final CouponReadService couponReadService;

    @Transactional
    public void issueCoupon() {
        CouponIssue couponIssue = redisTemplate.opsForList().rightPop(COUPON_DATA_QUEUE);
        if (couponIssue == null) {
            return;
        }

        Coupon coupon = couponReadService.getByIdWithLock(couponIssue.couponId());
        if (!coupon.isAvailableIssue()) { // 유효성 검사
            couponLogService.failIssue(coupon, couponIssue);
            return;
        }

        coupon.issue();
        couponLogService.successIssue(coupon, couponIssue);
    }
}

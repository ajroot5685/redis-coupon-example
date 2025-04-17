package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    private final CouponReadService couponReadService;
    private final CouponLogService couponLogService;
    private final RedisTemplate<String, CouponIssue> redisTemplate;

    public CouponIssueResultDto requestCouponIssue(CouponIssue couponIssueDto) {
        Coupon coupon = couponReadService.getById(couponIssueDto.couponId());

        // 유효성 검사
        if (!coupon.isAvailableIssue()) {
            return couponLogService.failIssue(coupon, couponIssueDto);
        }

        redisTemplate.opsForList().leftPush("coupon:queue", couponIssueDto); // 쿠폰발급 데이터 추가
        redisTemplate.convertAndSend("coupon:issue", "쿠폰발급 요청 도착"); // publish

        return new CouponIssueResultDto(true, coupon.getName());
    }
}

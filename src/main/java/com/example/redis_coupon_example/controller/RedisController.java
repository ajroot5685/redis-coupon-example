package com.example.redis_coupon_example.controller;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redis/coupons")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/{couponId}/issue/users/{userId}")
    public CouponIssueResultDto issue(@PathVariable("couponId") Long couponId, @PathVariable("userId") String userId) {
        return redisService.requestCouponIssue(new CouponIssue(couponId, userId));
    }

}

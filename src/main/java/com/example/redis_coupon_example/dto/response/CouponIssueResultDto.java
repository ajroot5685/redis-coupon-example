package com.example.redis_coupon_example.dto.response;

public record CouponIssueResultDto(
        boolean success,
        String couponName
) {
}

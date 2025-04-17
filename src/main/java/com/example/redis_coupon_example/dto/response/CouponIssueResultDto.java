package com.example.redis_coupon_example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponIssueResultDto {

    private boolean success;
    private String couponName;
}

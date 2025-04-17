package com.example.redis_coupon_example.controller;

import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic/coupons")
@RequiredArgsConstructor
public class BasicController {

    private final BasicService basicService;

    @PostMapping("/{couponId}/issue/users/{userId}")
    public CouponIssueResultDto issue(@PathVariable("couponId") Long couponId, @PathVariable("userId") String userId) {
        return basicService.issueCoupon(couponId, userId);
    }

}

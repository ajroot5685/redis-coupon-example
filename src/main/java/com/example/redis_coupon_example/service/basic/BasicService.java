package com.example.redis_coupon_example.service.basic;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.repository.CouponRepository;
import com.example.redis_coupon_example.service.CouponLogService;
import com.example.redis_coupon_example.service.CouponReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BasicService {

    private final CouponReadService couponReadService;
    private final CouponLogService couponLogService;
    private final CouponRepository couponRepository;

    public CouponIssueResultDto issueCoupon(CouponIssue couponIssueDto) {
        Coupon coupon = couponReadService.getById(couponIssueDto.couponId());

        // 유효성 검사
        if (!coupon.isAvailableIssue()) {
            return couponLogService.failIssue(coupon, couponIssueDto);
        }

        // 쿠폰 발급
        coupon.issue();
        couponRepository.save(coupon);

        return couponLogService.successIssue(coupon, couponIssueDto);
    }
}

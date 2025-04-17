package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.entity.CouponLog;
import com.example.redis_coupon_example.repository.CouponLogRepository;
import com.example.redis_coupon_example.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BasicService {

    private final CouponRepository couponRepository;
    private final CouponLogRepository couponLogRepository;

    public CouponIssueResultDto issueCoupon(CouponIssue couponIssueDto) {
        Coupon coupon = couponRepository.findByIdWithLock(couponIssueDto.couponId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 쿠폰이 없습니다."));

        // 유효성 검사
        coupon.validateIssueAvailability();
//        SleepUtil.sleep(250);

        // 쿠폰 발급
        coupon.issue();
        couponRepository.save(coupon);

        // 쿠폰 발급 기록 저장
        CouponLog couponLog = CouponLog.create(coupon, couponIssueDto.userId());
        couponLogRepository.save(couponLog);

        return new CouponIssueResultDto(true, coupon.getName());
    }
}

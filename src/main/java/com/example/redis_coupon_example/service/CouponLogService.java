package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.dto.CouponIssue;
import com.example.redis_coupon_example.dto.response.CouponIssueResultDto;
import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.entity.CouponLog;
import com.example.redis_coupon_example.entity.CouponLogStatus;
import com.example.redis_coupon_example.repository.CouponLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponLogService {

    private final CouponLogRepository couponLogRepository;

    public CouponIssueResultDto successIssue(Coupon coupon, CouponIssue couponIssueDto) {
        saveLog(coupon, couponIssueDto.userId(), CouponLogStatus.SUCCESS);
        return new CouponIssueResultDto(true, coupon.getName());
    }

    public CouponIssueResultDto failIssue(Coupon coupon, CouponIssue couponIssueDto) {
        saveLog(coupon, couponIssueDto.userId(), CouponLogStatus.FAIL);
        return new CouponIssueResultDto(false, coupon.getName());
    }

    private void saveLog(Coupon coupon, String userId, CouponLogStatus status) {
        CouponLog couponLog = CouponLog.create(coupon, userId, status);
        couponLogRepository.save(couponLog);
    }
}

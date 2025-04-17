package com.example.redis_coupon_example.service;

import com.example.redis_coupon_example.entity.Coupon;
import com.example.redis_coupon_example.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponReadService {

    private final CouponRepository couponRepository;

    public Coupon getById(Long id) {
        return couponRepository.findByIdWithLock(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 쿠폰이 없습니다."));
    }
}

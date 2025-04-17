package com.example.redis_coupon_example.repository;

import com.example.redis_coupon_example.entity.CouponLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponLogRepository extends JpaRepository<CouponLog, Long> {
}

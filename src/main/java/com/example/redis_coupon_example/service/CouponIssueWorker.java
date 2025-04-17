package com.example.redis_coupon_example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponIssueWorker {

    private final CouponIssueWorkerLogic couponIssueWorkerLogic;

    public void onIssueMessage(String message) {
        couponIssueWorkerLogic.logic();
    }
}

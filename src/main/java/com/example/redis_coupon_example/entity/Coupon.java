package com.example.redis_coupon_example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int totalCount;

    private int issuedCount;


    public void issue() {
        issuedCount++;
    }

    public void validateIssueAvailability() {
        if (issuedCount >= totalCount) {
            throw new IllegalArgumentException("남은 수량이 없습니다.");
        }
    }
}

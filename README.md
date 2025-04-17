# redis-coupon-example

올리브영 - Redis Pub/Sub을 활용한 쿠폰 발급 비동기 처리

https://oliveyoung.tech/2023-08-07/async-process-of-coupon-issuance-using-redis/

## 📌 목표

1. 수만명이 몰리는 트래픽에도 쿠폰 다운로드 시간을 최소화한다.(5s 미만)
2. Redis를 사용하여 성능을 챙기면서도, 작업이 유실되지 않도록 안전하게 정합성을 보장한다.
3. Redis를 도입했을 때의 성능이 유의미하게 관측되어야 한다 - TPS 최소 1.5배 이상 또는 지연시간 30% 이상 개선

## ✨ 기능

- Redis가 없는 쿠폰 발급 API
- Redis가 있는 쿠폰 발급 API

## 👀 고려사항

- 수량 등 쿠폰 발급 유효성 검사는 어떻게 할 것인가?(동시성)
- 쿠폰 발급 워커는 어떻게 구상할 것인가?
- pub/sub을 어떻게 활용할 것인가?
- list를 어떻게 활용할 것인가?

## ⚙️ 테스트 환경

사용도구 : JMeter

설정

- Nubmer of Threads(users) : 1000
- Ramp-up period : 1
- Loop Count: 100

주의사항

- HTTP 요청을 보내면서 캐싱을 수행하지 않도록 해야한다
- `CSV Data Set Config`를 활용하여 각 요청마다 다른 userId를 부여한다
- `Same user on each iteration`을 해제하여 중복된 userId가 생기지 않도록 한다

## ✔️ 테스트 결과(TPS)

개선 전 일반 발급 API : 372

레디스를 이용한 발급 API : ?

## 🛠️ 정합성 보장 방법

개선 전 일반 발급 로직

- `@Lock`을 사용하여 트랜잭션 동안 다른 쓰레드가 읽지도, 쓰지도 못하게 설정

레디스를 이용한 발급 로직

package com.app.infrastructure;

import com.app.domain.Account;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisInitializer {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisInitializer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initialize() {
        redisTemplate.opsForValue().set(getAccount1().getId(), getAccount1());
        redisTemplate.opsForValue().set(getAccount2().getId(), getAccount2());
    }

    private static Account getAccount1() {
        return new Account(
                "1",
                "123",
                1000.00,
                500.00,
                200.00);
    }

    private static Account getAccount2() {
        return new Account(
                "2",
                "456",
                1500.00,
                800.00,
                300.00);
    }
}


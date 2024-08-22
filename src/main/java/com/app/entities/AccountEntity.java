package com.app.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Account")
public class AccountEntity implements Serializable {

    @Id
    private String id;
    private double foodBalance;
    private double mealBalance;
    private double cashBalance;
}

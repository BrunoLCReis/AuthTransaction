package com.app.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Transaction")
public class TransactionEntity implements Serializable {

    @Id
    private String id;
    private String accountId;
    private double amount;
    private String mcc;
    private String merchant;
}

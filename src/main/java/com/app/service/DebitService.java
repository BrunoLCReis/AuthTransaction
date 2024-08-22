package com.app.service;

import com.app.domain.Account;
import com.app.enums.Category;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.app.enums.Category.CASH;

@Service
public class DebitService {

    private final RedisTemplate<String, Object> redisTemplate;


    public DebitService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean debitByCategory(Category category, double amount, Account account) {
        Double currentBalance = getBalanceForCategory(account, category);
        if (currentBalance != null && currentBalance >= amount) {
            updateBalanceForCategory(account, category, currentBalance - amount);
            return true;
        } else if (currentBalance != null) {
            if (account.getCashBalance() >= amount) {
                updateBalanceForCategory(account, CASH, account.getCashBalance() - amount);
                return true;
            }
        }
        return false;
    }

    private Double getBalanceForCategory(Account account, Category category) {
        return switch (category) {
            case FOOD, PICPAY -> account.getFoodBalance();
            case MEAL, UBER -> account.getMealBalance();
            default -> account.getCashBalance();
        };
    }

    private void updateBalanceForCategory(Account account, Category category, double newBalance) {
        switch (category) {
            case FOOD, PICPAY -> account.setFoodBalance(newBalance);
            case MEAL, UBER -> account.setMealBalance(newBalance);
            default -> account.setCashBalance(newBalance);
        }
        saveAccount(account);
    }

    private void saveAccount(Account account) {
        redisTemplate.opsForValue().set(account.getId(), account);
    }
}

package com.app.service;

import com.app.domain.Account;
import com.app.domain.Transaction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private static final String TRANSACTION_SUCCESS = "00";
    private static final String OPERATION_ERROR = "07";
    private static final String TRANSACTION_REJECTED = "51";

    private static final String FOOD = "FOOD";
    private static final String MEAL = "MEAL";
    private static final String PICPAY = "PICPAY";
    private static final String UBER = "UBER";
    private static final String CASH = "CASH";

    private static final String FOOD_MCC_1 = "5411";
    private static final String FOOD_MCC_2 = "5412";
    private static final String MEAL_MCC_1 = "5811";
    private static final String MEAL_MCC_2 = "5812";
    private static final String ALTERNATIVE_UBER_MCC = "5877";
    private static final String ALTERNATIVE_PICPAY_MCC = "6104";

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorizationService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String authorize(Transaction transaction) {
        Account account = getAccountByTransactionId(transaction.getId());
        if (account == null) {
            return OPERATION_ERROR;
        }

        double amount = transaction.getAmount();
        String establishmentMCC = getAlternativeMcc(transaction.getMerchant(), transaction.getMcc());
        String category = mapEstablishmentCategory(establishmentMCC);

        return debitFromCategory(category, amount, account) ? TRANSACTION_SUCCESS : TRANSACTION_REJECTED;
    }

    private String mapEstablishmentCategory(String establishmentMCC) {
        return switch (establishmentMCC) {
            case FOOD_MCC_1, FOOD_MCC_2 -> FOOD;
            case MEAL_MCC_1, MEAL_MCC_2 -> MEAL;
            case ALTERNATIVE_PICPAY_MCC -> PICPAY;
            case ALTERNATIVE_UBER_MCC -> UBER;
            default -> CASH;
        };
    }

    private boolean debitFromCategory(String category, double amount, Account account) {
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

    private Double getBalanceForCategory(Account account, String category) {
        return switch (category) {
            case FOOD, PICPAY -> account.getFoodBalance();
            case MEAL, UBER -> account.getMealBalance();
            default -> account.getCashBalance();
        };
    }

    private void updateBalanceForCategory(Account account, String category, double newBalance) {
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

    private String getAlternativeMcc(String merchant, String currentMcc) {
        if (merchant.contains("UBER")) {
            return ALTERNATIVE_UBER_MCC;
        }
        if (merchant.contains("PICPAY")) {
            return ALTERNATIVE_PICPAY_MCC;
        }
        return currentMcc;
    }

    public Account getAccountByTransactionId(String transactionId) {
        return (Account) redisTemplate.opsForValue().get(transactionId);
    }
}

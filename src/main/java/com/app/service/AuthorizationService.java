package com.app.service;

import com.app.domain.Account;
import com.app.domain.Transaction;
import com.app.enums.Category;
import com.app.enums.MCC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.app.enums.Category.*;
import static com.app.enums.MCC.ALTERNATIVE_PICPAY_MCC;
import static com.app.enums.MCC.ALTERNATIVE_UBER_MCC;

@Service
public class AuthorizationService {

    private static final String TRANSACTION_SUCCESS = "00";
    private static final String OPERATION_ERROR = "07";
    private static final String TRANSACTION_REJECTED = "51";

    private final RedisTemplate<String, Object> redisTemplate;
    private final DebitService debitService;

    public AuthorizationService(RedisTemplate<String, Object> redisTemplate, DebitService debitService) {
        this.redisTemplate = redisTemplate;
        this.debitService = debitService;
    }

    public String authorize(Transaction transaction) {
        Account account = getAccountByTransactionId(transaction.getId());
        if (account == null) {
            return OPERATION_ERROR;
        }

        double amount = transaction.getAmount();
        MCC establishmentMCC = getAlternativeMcc(transaction.getMerchant(), transaction.getMcc());
        Category category = mapEstablishmentCategory(establishmentMCC);

        return debitService.debitByCategory(category, amount, account) ?
                TRANSACTION_SUCCESS :
                TRANSACTION_REJECTED;
    }

    private Category mapEstablishmentCategory(MCC establishmentRate) {
        return switch (establishmentRate) {
            case FOOD_RATE_1, FOOD_RATE_2 -> FOOD;
            case MEAL_RATE_1, MEAL_RATE_2 -> MEAL;
            case ALTERNATIVE_PICPAY_MCC -> PICPAY;
            case ALTERNATIVE_UBER_MCC -> UBER;
            default -> CASH;
        };
    }

    private MCC getAlternativeMcc(String merchant, String currentMcc) {
        if (merchant.contains("UBER")) {
            return ALTERNATIVE_UBER_MCC;
        }
        if (merchant.contains("PICPAY")) {
            return ALTERNATIVE_PICPAY_MCC;
        }
        return MCC.fromCode(currentMcc);
    }

    public Account getAccountByTransactionId(String transactionId) {
        return (Account) redisTemplate.opsForValue().get(transactionId);
    }
}

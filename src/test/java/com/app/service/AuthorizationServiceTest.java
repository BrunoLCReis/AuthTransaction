package com.app.service;

import com.app.domain.Account;
import com.app.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthorizationService.class)
public class AuthorizationServiceTest {

    @Autowired
    private AuthorizationService authorizationService;

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testEstablishmentRate5411UseFoodBalance() {
        Transaction transaction = getTransaction("1", "5411");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double initialAmount = getAccount().getFoodBalance();
        Double finalAmount = initialAmount - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getFoodBalance(), finalAmount);
    }

    @Test
    public void testEstablishmentRate5412UseFoodBalance() {
        Transaction transaction = getTransaction("2", "5412");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double initialAmount = getAccount().getFoodBalance();
        Double finalAmount = initialAmount - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getFoodBalance(), finalAmount);
    }

    @Test
    public void testEstablishmentRate5811UseMealBalance() {
        Transaction transaction = getTransaction("3", "5811");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double initialAmount = getAccount().getMealBalance();
        Double finalAmount = initialAmount - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getMealBalance(), finalAmount);
    }

    @Test
    public void testEstablishmentRate5812UseMealBalance() {
        Transaction transaction = getTransaction("4", "5812");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double initialAmount = getAccount().getMealBalance();
        Double finalAmount = initialAmount - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getMealBalance(), finalAmount);
    }

    @Test
    public void testEstablishmentOtherKindOfRateUseCashBalance() {
        Transaction transaction = getTransaction("76", "0000");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double finalAmount = getAccount().getCashBalance() - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getCashBalance(), finalAmount);
    }

    @Test
    public void testEstablishmentOtherKindOfRateUseCashBalanceError() {
        Transaction transaction = getTransaction("55", "0000");
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double finalAmount = getAccount().getCashBalance() - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getCashBalance(), finalAmount);
    }

    @Test
    public void testCashBalanceSmallerThanAmountSentMustReturn51() {
        Transaction transaction = getTransaction("5", "0000");
        Account account = getAccountWithLowCash();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        assertNotNull(result);
        assertEquals(result, "51");
        assertEquals(account.getCashBalance(), getAccountWithLowCash().getCashBalance());
    }

    @Test
    public void testFoodBalanceSmallerThanAmountSentMustUseCashBalance() {
        Transaction transaction = getTransaction("66", "5411");
        Account account = getAccountFoodBalanceCantHandle();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double finalAmount = getAccountFoodBalanceCantHandle().getCashBalance() - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getCashBalance(), finalAmount);
        assertEquals(account.getFoodBalance(), getAccountFoodBalanceCantHandle().getFoodBalance());
    }

    @Test
    public void testUberAlternativeCategory() {
        Transaction transaction = getTransactionUberMerchant();
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double finalAmount = getAccount().getMealBalance() - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getMealBalance(), finalAmount);
    }

    @Test
    public void testPicpayAlternativeCategory() {
        Transaction transaction = getTransactionPicpayMerchant();
        Account account = getAccount();

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        Double finalAmount = getAccount().getFoodBalance() - transaction.getAmount();

        assertNotNull(result);
        assertEquals(result, "00");
        assertEquals(account.getFoodBalance(), finalAmount);
    }

    @Test
    public void testAccountNull() {
        Transaction transaction = getTransaction("98", "5734");

        when(valueOperations.get(anyString())).thenReturn(null);

        String result = authorizationService.authorize(transaction);

        assertNotNull(result);
        assertEquals(result, "07");
    }

    @Test
    public void testCurrentBalanceNull() {
        Transaction transaction = getTransaction("98", "5734");
        Account account = getAccount();
        account.setCashBalance(null);

        when(valueOperations.get(anyString())).thenReturn(account);

        String result = authorizationService.authorize(transaction);

        assertNotNull(result);
        assertEquals(result, "51");
    }

    private static Transaction getTransaction(String id, String rate) {
        return new Transaction(id, "500", 100.00, rate, "PADARIA DO ZE - SAO PAULO BR");
    }

    private static Transaction getTransactionUberMerchant() {
        return new Transaction("25", "500", 100.00, "4343", "UBER EATS");
    }

    private static Transaction getTransactionPicpayMerchant() {
        return new Transaction("59", "500", 100.00, "2312", "PICPAY PAG@");
    }

    private static Account getAccount() {
        return new Account("1", "500", 1000.00, 500.00, 200.00);
    }

    private static Account getAccountWithLowCash() {
        return new Account("1", "500", 50.00, 500.00, 200.00);
    }

    private static Account getAccountFoodBalanceCantHandle() {
        return new Account("1", "500", 800.00, 10.00, 200.00);
    }

}

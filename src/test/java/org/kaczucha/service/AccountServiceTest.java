package org.kaczucha.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.repository.AccountRepository;
import org.kaczucha.repository.entity.Account;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountService service;
    private AccountRepository repository;

    @BeforeEach
    public void setup() {
        repository = mock(AccountRepository.class);
        final AccountMapper mapper = mock(AccountMapper.class);
        final CurrencyService currencyService = mock(CurrencyService.class);
        service = new AccountService(repository, mapper, currencyService);
    }

    @Test
    public void transfer_allParamsOk_sameCurrency_fundsTransferred() {
        //given
        final long fromAccountId = 1;
        final long toAccountId = 2;
        final String currency = "PLN";
        final Account accountFrom = new Account(
                fromAccountId,
                100.0,
                currency,
                1L);
        final Account accountTo = new Account(
                toAccountId,
                100.0,
                currency,
                2L);
        final double amount = 50;

        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        when(repository.getOne(toAccountId))
                .thenReturn(accountTo);

        //when
        service.transfer(fromAccountId, toAccountId, currency, amount);
        //then
        final Account expectedAccountFrom = new Account(
                fromAccountId,
                50.0,
                currency,
                1L
        );
        final Account expectedAccountTo = new Account(
                toAccountId,
                150.0,
                currency,
                2L
        );

        verify(repository).save(expectedAccountFrom);
        verify(repository).save(expectedAccountTo);
    }

//    @Test
//    public void transfer_allFunds_fundsTransferred() {
//        //given
//        final long fromAccountId = 1;
//        final long toAccountId = 2;
//        final double fromBalance = 100.0;
//        final Account accountFrom = new Account(
//                fromAccountId,
//                fromBalance,
//                "PLN",
//                1L);
//        final Account accountTo = new Account(
//                toAccountId,
//                100.0,
//                "PLN",
//                2L);
//
//        when(repository.getOne(fromAccountId))
//                .thenReturn(accountFrom);
//        when(repository.getOne(toAccountId))
//                .thenReturn(accountTo);
//        //when
//        service.transfer(fromAccountId, toAccountId, fromBalance);
//        //then
//        final Account expectedAccountFrom = new Account(
//                fromAccountId,
//                0.0,
//                "PLN",
//                1L
//        );
//        final Account expectedAccountTo = new Account(
//                toAccountId,
//                200.0,
//                "PLN",
//                2L
//        );
//
//        verify(repository).save(expectedAccountFrom);
//        verify(repository).save(expectedAccountTo);
//    }
//
//    @Test
//    public void transfer_notEnoughFunds_thrownNoSufficientFundsException() {
//        //given
//        final long fromAccountId = 1;
//        final long toAccountId = 2;
//        final double fromBalance = 100.0;
//        final Account accountFrom = new Account(
//                fromAccountId,
//                fromBalance,
//                "PLN",
//                1L);
//        final Account accountTo = new Account(
//                toAccountId,
//                100.0,
//                "PLN",
//                2L);
//        final double amount = fromBalance + 100.0;
//
//        when(repository.getOne(fromAccountId))
//                .thenReturn(accountFrom);
//        when(repository.getOne(toAccountId))
//                .thenReturn(accountTo);
//        //when/then
//        Assertions.assertThrows(
//                NoSufficientFundsException.class,
//                () -> service.transfer(fromAccountId, toAccountId, amount));
//
//    }
//
//    @Test
//    public void transfer_negativeAmount_thrownIllegalArgumentException() {
//        //given
//        final long fromAccountId = 1;
//        final long toAccountId = 2;
//        final double amount = -100.0;
//        //when/then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.transfer(fromAccountId, toAccountId, amount));
//    }
//
//    @Test
//    public void transfer_zeroAmount_thrownIllegalArgumentException() {
//        //given
//        final long fromAccountId = 1;
//        final long toAccountId = 2;
//        final double amount = 0;
//        //when/then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.transfer(fromAccountId, toAccountId, amount));
//    }
//
//    @Test
//    public void transfer_toSameAccount_thrownException() {
//        //given
//        final long fromAccountId = 1;
//        //when/then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.transfer(fromAccountId, fromAccountId, 10));
//    }

    @Test
    public void withdraw_correctAmount_balanceChangedCorrectly() {
        //given
        final long fromAccountId = 1;
        final Account accountFrom = new Account(
                fromAccountId,
                100.0,
                "PLN",
                1L);
        final double amount = 50;
        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        //when
        service.withdraw(fromAccountId, amount);
        //then
        Account expectedAccountFrom = new Account(
                fromAccountId,
                50.0,
                "PLN",
                1L);
        verify(repository).save(expectedAccountFrom);
    }

    @Test
    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
        //given
        final long fromAccountId = 1;
        final Account accountFrom = new Account(
                fromAccountId,
                100.0,
                "PLN",
                1L);
        final double amount = 50.5;
        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        //when
        service.withdraw(fromAccountId, amount);
        //then
        Account expectedAccountFrom = new Account(
                fromAccountId,
                49.5,
                "PLN",
                1L);
        verify(repository).save(expectedAccountFrom);
    }

    @Test
    public void withdraw_allBalance_balanceSetToZero() {
        //given
        final long fromAccountId = 1;
        final double fromBalance = 100.0;
        final Account accountFrom = new Account(
                fromAccountId,
                fromBalance,
                "PLN",
                1L);
        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        //when
        service.withdraw(fromAccountId, fromBalance);
        //then
        Account expectedAccountFrom = new Account(
                fromAccountId,
                0.0,
                "PLN",
                1L);
        verify(repository).save(expectedAccountFrom);
    }

    @Test
    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
        //given
        final long fromAccountId = 1;
        final double amount = -100.0;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.withdraw(fromAccountId, amount)
        );
    }

    @Test
    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
        //given
        final long fromAccountId = 1;
        final double amount = 0;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.withdraw(fromAccountId, amount)
        );
    }

    @Test
    public void withdraw_amountBiggerThanBalance_throwsNoSufficientFundsException() {
        //given
        final long fromAccountId = 1;
        final double fromBalance = 100.0;
        final Account accountFrom = new Account(
                fromAccountId,
                fromBalance,
                "PLN",
                1L);
        final double amount = fromBalance + 100;
        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        //when/then
        Assertions.assertThrows(
                NoSufficientFundsException.class,
                () -> service.withdraw(fromAccountId, amount)
        );
    }
}


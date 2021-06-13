package org.kaczucha.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.controller.dto.AccountResponse;
import org.kaczucha.repository.AccountRepository;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;

import java.util.Optional;

import static java.util.Collections.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountService service;
    private AccountRepository repository;

    @BeforeEach
    public void setup() {
        repository = mock(AccountRepository.class);
        final AccountMapper mapper = mock(AccountMapper.class);
        service = new AccountService(repository, mapper);
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //given
        final long fromAccountId = 1;
        final long toAccountId = 2;
        final Account accountFrom = new Account(
                fromAccountId,
                100.0,
                "PLN",
                1L);
        final Account accountTo = new Account(
                toAccountId,
                100.0,
                "PLN",
                2L);
        final double amount = 50;

        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        when(repository.getOne(toAccountId))
                .thenReturn(accountTo);

        //when
        service.transfer(fromAccountId, toAccountId, amount);
        //then
        final Account expectedAccountFrom = new Account(
                fromAccountId,
                50.0,
                "PLN",
                1L
        );
        final Account expectedAccountTo = new Account(
                toAccountId,
                150.0,
                "PLN",
                2L
        );

        verify(repository).save(expectedAccountFrom);
        verify(repository).save(expectedAccountTo);
    }

    @Test
    public void transfer_allFunds_fundsTransferred() {
        //given
        final long fromAccountId = 1;
        final long toAccountId = 2;
        final double fromBalance = 100.0;
        final Account accountFrom = new Account(
                fromAccountId,
                fromBalance,
                "PLN",
                1L);
        final Account accountTo = new Account(
                toAccountId,
                100.0,
                "PLN",
                2L);

        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        when(repository.getOne(toAccountId))
                .thenReturn(accountTo);
        //when
        service.transfer(fromAccountId, toAccountId, fromBalance);
        //then
        final Account expectedAccountFrom = new Account(
                fromAccountId,
                0.0,
                "PLN",
                1L
        );
        final Account expectedAccountTo = new Account(
                toAccountId,
                200.0,
                "PLN",
                2L
        );

        verify(repository).save(expectedAccountFrom);
        verify(repository).save(expectedAccountTo);
    }

    @Test
    public void transfer_notEnoughFunds_thrownNoSufficientFundsException() {
        //given
        final long fromAccountId = 1;
        final long toAccountId = 2;
        final double fromBalance = 100.0;
        final Account accountFrom = new Account(
                fromAccountId,
                fromBalance,
                "PLN",
                1L);
        final Account accountTo = new Account(
                toAccountId,
                100.0,
                "PLN",
                2L);
        final double amount = fromBalance + 100.0;

        when(repository.getOne(fromAccountId))
                .thenReturn(accountFrom);
        when(repository.getOne(toAccountId))
                .thenReturn(accountTo);
        //when/then
        Assertions.assertThrows(
                NoSufficientFundsException.class,
                () -> service.transfer(fromAccountId, toAccountId, amount));

    }

    @Test
    public void transfer_negativeAmount_thrownIllegalArgumentException() {
        //given
        final long fromAccountId = 1;
        final long toAccountId = 2;
        final double amount = -100.0;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(fromAccountId, toAccountId, amount));
    }

    @Test
    public void transfer_toSameAccount_thrownException() {
        //given
        final long fromAccountId = 1;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(fromAccountId, fromAccountId, 10));
    }

//    @Test
//    public void withdraw_correctAmount_balanceChangedCorrectly() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(100, "PLN")));
//        when(repository.findByEmail(email)).thenReturn(client);
//        //when
//        service.withdraw(email, 50);
//        //then
//        Client expectedClient = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(50, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(100, "PLN")));
//        when(repository.findByEmail(email)).thenReturn(client);
//        //when
//        service.withdraw(email, 50.5);
//        //then
//        Client expectedClient = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(49.5, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_allBalance_balanceSetToZero() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(100, "PLN")));
//        when(repository.findByEmail(email)).thenReturn(client);
//        //when
//        service.withdraw(email, 100);
//        //then
//        Client expectedClient = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(0, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//
//    @Test
//    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
//        //given
//        final String email = "a@a.pl";
//        int amount = -100;
//        //when
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
//        //given
//        final String email = "a@a.pl";
//        int amount = 0;
//        //when
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_amountBiggerThanBalance_throwsNoSufficientFundsException() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client(
//                "Alek",
//                email,
//                singletonList(new Account(100, "PLN")));
//        when(repository.findByEmail(email)).thenReturn(client);
//        int amount = 1000;
//        //when/then
//        Assertions.assertThrows(
//                NoSufficientFundsException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//    @Test
//    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
//        //given
//        final String email = "A@A.PL";
//        final String lowerEmail = "a@a.pl";
//        final Client client = new Client(
//                "Alek",
//                lowerEmail,
//                singletonList(new Account(100, "PLN")));
//        when(repository.findByEmail(lowerEmail)).thenReturn(client);
//        //when
//        service.withdraw(email, 50);
//        //then
//        final Client expectedClient = new Client(
//                "Alek",
//                lowerEmail,
//                singletonList(new Account(50, "PLN")));
//        verify(repository).save(expectedClient);
//    }
//    @Test
//    public void withdraw_nullEmail_throwsIllegalArgumentException() {
//        //given
//        final String email = null;
//        int amount = 1000;
//        //when/then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
}


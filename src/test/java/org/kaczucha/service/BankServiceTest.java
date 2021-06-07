package org.kaczucha.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.repository.ClientSpringJpaRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import static java.util.Collections.*;
import static org.mockito.Mockito.*;

public class BankServiceTest {
    private BankService service;
    private ClientSpringJpaRepository repository;

    @BeforeEach
    public void setup() {
        repository = mock(ClientSpringJpaRepository.class);
        ClientMapper mapper = mock(ClientMapper.class);
        service = new BankService(repository, mapper);
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(1000, "PLN")));
        final Client clientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(500, "PLN")));
        final double amount = 100;

        when(repository.findByEmail(emailFrom))
                .thenReturn(clientFrom);
        when(repository.findByEmail(emailTo))
                .thenReturn(clientTo);

        //when
        service.transfer(emailFrom, emailTo, amount);
        //then
        final Client expectedClientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(900, "PLN")));
        final Client expectedClientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(600, "PLN")));

        verify(repository).save(expectedClientFrom);
        verify(repository).save(expectedClientTo);
    }

    @Test
    public void transfer_allFunds_fundsTransferred() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(1000, "PLN")));
        final Client clientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(500, "PLN")));

        final double amount = 1000;
        when(repository.findByEmail(emailFrom))
                .thenReturn(clientFrom);
        when(repository.findByEmail(emailTo))
                .thenReturn(clientTo);
        //when
        service.transfer(emailFrom, emailTo, amount);
        //then
        final Client expectedClientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(0, "PLN")));
        final Client expectedClientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(1500, "PLN")));
        final SoftAssertions softAssertions = new SoftAssertions();

        verify(repository).save(expectedClientFrom);
        verify(repository).save(expectedClientTo);
    }

    @Test
    public void transfer_notEnoughFunds_thrownNoSufficientFundsException() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(100, "PLN")));
        final Client clientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(500, "PLN")));
        when(repository.findByEmail(emailFrom)).thenReturn(clientFrom);
        when(repository.findByEmail(emailTo)).thenReturn(clientTo);
        final double amount = 1000;
        //when/then
        Assertions.assertThrows(
                NoSufficientFundsException.class,
                () -> service.transfer(emailFrom, emailTo, amount));

    }

    @Test
    public void transfer_negativeAmount_thrownIllegalArgumentException() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final double amount = -1000;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(emailFrom, emailTo, amount));
    }

    @Test
    public void transfer_toSameClient_thrownException() {
        //given
        final String email = "a@a.pl";
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(email, email, 10));
    }

    @Test
    public void withdraw_correctAmount_balanceChangedCorrectly() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client(
                "Alek",
                email,
                singletonList(new Account(100, "PLN")));
        when(repository.findByEmail(email)).thenReturn(client);
        //when
        service.withdraw(email, 50);
        //then
        Client expectedClient = new Client(
                "Alek",
                email,
                singletonList(new Account(50, "PLN")));
        verify(repository).save(expectedClient);
    }

    @Test
    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client(
                "Alek",
                email,
                singletonList(new Account(100, "PLN")));
        when(repository.findByEmail(email)).thenReturn(client);
        //when
        service.withdraw(email, 50.5);
        //then
        Client expectedClient = new Client(
                "Alek",
                email,
                singletonList(new Account(49.5, "PLN")));
        verify(repository).save(expectedClient);
    }

    @Test
    public void withdraw_allBalance_balanceSetToZero() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client(
                "Alek",
                email,
                singletonList(new Account(100, "PLN")));
        when(repository.findByEmail(email)).thenReturn(client);
        //when
        service.withdraw(email, 100);
        //then
        Client expectedClient = new Client(
                "Alek",
                email,
                singletonList(new Account(0, "PLN")));
        verify(repository).save(expectedClient);
    }

    @Test
    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
        //given
        final String email = "a@a.pl";
        int amount = -100;
        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
        //given
        final String email = "a@a.pl";
        int amount = 0;
        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }

    @Test
    public void withdraw_amountBiggerThanBalance_throwsNoSufficientFundsException() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client(
                "Alek",
                email,
                singletonList(new Account(100, "PLN")));
        when(repository.findByEmail(email)).thenReturn(client);
        int amount = 1000;
        //when/then
        Assertions.assertThrows(
                NoSufficientFundsException.class,
                () -> service.withdraw(email, amount)
        );
    }
    @Test
    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
        //given
        final String email = "A@A.PL";
        final String lowerEmail = "a@a.pl";
        final Client client = new Client(
                "Alek",
                lowerEmail,
                singletonList(new Account(100, "PLN")));
        when(repository.findByEmail(lowerEmail)).thenReturn(client);
        //when
        service.withdraw(email, 50);
        //then
        final Client expectedClient = new Client(
                "Alek",
                lowerEmail,
                singletonList(new Account(50, "PLN")));
        verify(repository).save(expectedClient);
    }
    @Test
    public void withdraw_nullEmail_throwsIllegalArgumentException() {
        //given
        final String email = null;
        int amount = 1000;
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.withdraw(email, amount)
        );
    }
}


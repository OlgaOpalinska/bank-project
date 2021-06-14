package org.kaczucha.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue
    @Column(name="ACCOUNT_ID")
    private long id;
    @Column(name="BALANCE")
    private double balance;
    @Column(name="CURRENCY")
    private String currency;
    @Column(name="USER_ID")
    private Long userId;


    public Account(final double balance, final String currency) {
        this.balance = balance;
        this.currency = currency;
    }
    public void setBalance(double balance) {
        this.balance = (double) Math.round(balance*100)/100;
    }
}

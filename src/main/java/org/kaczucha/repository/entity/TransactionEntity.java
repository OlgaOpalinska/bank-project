package org.kaczucha.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="TRANSACTIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    @GeneratedValue
    @Column(name="TRANSACTION_ID")
    private long id;
    @Column(name="AMOUNT")
    private double amount;
    @Column(name="CURRENCY")
    private String currency;
    @Column(name="TRANSACTION_DATE")
    private OffsetDateTime transactionDate;
    @Column(name="FROM_ACCOUNT_ID")
    private long fromAccountId;
    @Column(name="TO_ACCOUNT_ID")
    private long toAccountId;

}

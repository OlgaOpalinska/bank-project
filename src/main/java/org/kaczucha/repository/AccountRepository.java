package org.kaczucha.repository;

import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a where a.id = :id")
    Account findAccountById(@Param("id") Long id);
}

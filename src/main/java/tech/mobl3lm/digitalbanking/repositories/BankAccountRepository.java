package tech.mobl3lm.digitalbanking.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.mobl3lm.digitalbanking.enums.AccountStatus;
import java.util.Date;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    @Query("SELECT SUM(b.balance) FROM BankAccount b")
    double sumAllBalances();

    @Query("SELECT TYPE(b), COUNT(b), SUM(b.balance) FROM BankAccount b GROUP BY TYPE(b)")
    List<Object[]> groupByTypeAndSumBalances();

    long countByStatus(AccountStatus status);

    @Query("SELECT COUNT(b) FROM BankAccount b WHERE b.createdAt >= :date")
    long countByCreatedAtAfter(@Param("date") Date date);
    // You can add custom queries here if needed
}
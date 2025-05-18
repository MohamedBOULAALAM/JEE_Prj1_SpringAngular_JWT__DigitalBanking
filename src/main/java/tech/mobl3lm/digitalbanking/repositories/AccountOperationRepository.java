package tech.mobl3lm.digitalbanking.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import tech.mobl3lm.digitalbanking.entities.Operation;
import tech.mobl3lm.digitalbanking.enums.OperationType;

import java.util.Date;
import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<Operation, Long> {
    // Get all operations by BankAccount entity
    List<Operation> findByBankAccount(BankAccount bankAccount);
    // Get all operations by bank account ID
    @Query("SELECT o FROM Operation o WHERE o.bankAccount.id = :accountId")
    List<Operation> findByBankAccountId(String accountId);

    @Query("SELECT COUNT(o) FROM Operation o WHERE o.operationDate >= :startDate")
    long countRecent(@Param("startDate") Date startDate);


    long countByDescriptionContainingIgnoreCase(String keyword);


    // Delete all operations by bank account ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Operation o WHERE o.bankAccount.id = :id")
    void deleteByBankAccountId(String id);
    long countByOperationType(OperationType operationType);
}
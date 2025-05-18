package tech.mobl3lm.digitalbanking.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import tech.mobl3lm.digitalbanking.entities.Operation;
import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<Operation,Long> {
    List<Operation> findByBankAccount(BankAccount bankAccount);
}
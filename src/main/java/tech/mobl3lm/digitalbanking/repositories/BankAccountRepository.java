package tech.mobl3lm.digitalbanking.repositories;

import tech.mobl3lm.digitalbanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
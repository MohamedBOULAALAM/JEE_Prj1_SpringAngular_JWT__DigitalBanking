package tech.mobl3lm.digitalbanking.repositories;

import org.springframework.stereotype.Repository;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
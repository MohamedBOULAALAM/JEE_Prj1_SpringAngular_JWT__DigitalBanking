package tech.mobl3lm.digitalbanking.repositories;

import org.springframework.stereotype.Repository;
import tech.mobl3lm.digitalbanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByEmail(String email);
}
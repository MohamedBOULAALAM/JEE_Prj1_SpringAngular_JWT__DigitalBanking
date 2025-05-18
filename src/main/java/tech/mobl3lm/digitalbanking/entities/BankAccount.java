package tech.mobl3lm.digitalbanking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.mobl3lm.digitalbanking.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@SuperBuilder
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",length = 4)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;
    private AccountStatus status;
    private String currency;
    private Date createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<Operation> accountOperations;
}
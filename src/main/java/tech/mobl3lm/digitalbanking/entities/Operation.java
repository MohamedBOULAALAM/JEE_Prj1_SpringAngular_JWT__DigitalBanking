package tech.mobl3lm.digitalbanking.entities;

import jakarta.persistence.*;
import lombok.*;
import tech.mobl3lm.digitalbanking.enums.OperationType;

import java.util.Date;
@Getter
@Setter
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OperationType operationType;
    private double amount;
    private Date operationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBankAccount")
    private BankAccount bankAccount;
}
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
    private String id;
    private OperationType operationType;
    private double amount;
    private Date operationDate;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBankAccount")
    private BankAccount bankAccount;
}
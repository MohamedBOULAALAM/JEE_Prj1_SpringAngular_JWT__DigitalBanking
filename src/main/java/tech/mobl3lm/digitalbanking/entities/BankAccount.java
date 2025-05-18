package tech.mobl3lm.digitalbanking.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SavingAccount.class, name = "SAV"),
        @JsonSubTypes.Type(value = CurrentAccount.class, name = "CUR")
})
@AllArgsConstructor
@NoArgsConstructor
public abstract class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
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
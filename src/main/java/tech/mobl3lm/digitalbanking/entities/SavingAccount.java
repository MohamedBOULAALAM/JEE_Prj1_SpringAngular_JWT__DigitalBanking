package tech.mobl3lm.digitalbanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true) // ✅ inclut les champs de BankAccount
@SuperBuilder
@Entity
@Getter
@Setter
@Data
@DiscriminatorValue("SAV")
@AllArgsConstructor @NoArgsConstructor
public class SavingAccount  extends  BankAccount{
    private double interestRate;
}
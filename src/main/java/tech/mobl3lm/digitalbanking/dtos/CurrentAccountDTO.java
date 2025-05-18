package tech.mobl3lm.digitalbanking.dtos;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccountDTO extends BankAccountDTO {
    private double overdraft;
}
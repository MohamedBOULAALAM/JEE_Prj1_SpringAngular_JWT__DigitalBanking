package tech.mobl3lm.digitalbanking.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditDebitRequestDTO {
    private String accountId;
    private double amount;
    private String description;
}
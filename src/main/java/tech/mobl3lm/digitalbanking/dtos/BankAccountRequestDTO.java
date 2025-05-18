package tech.mobl3lm.digitalbanking.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccountRequestDTO {
    private String type; // "SAV" or "CUR"
    private double initialBalance;
    private String customerId;
    private Double interestRate; // for saving account
    private Double overdraft; // for current account
}
package tech.mobl3lm.digitalbanking.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.mobl3lm.digitalbanking.enums.OperationType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private String id;
    private OperationType operationType;
    private double amount;
    private String accountId;
}
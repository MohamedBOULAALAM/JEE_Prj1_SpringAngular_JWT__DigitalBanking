package tech.mobl3lm.digitalbanking.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.mobl3lm.digitalbanking.enums.OperationType;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountOperationDTO {
    private String id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private String description;
}
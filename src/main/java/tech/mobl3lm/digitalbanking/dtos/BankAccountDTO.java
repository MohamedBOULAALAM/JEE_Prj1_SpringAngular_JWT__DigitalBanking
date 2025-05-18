package tech.mobl3lm.digitalbanking.dtos;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private String status;
    private CustomerDTO customerDTO;
    private String type; // "SAV" or "CUR"
}
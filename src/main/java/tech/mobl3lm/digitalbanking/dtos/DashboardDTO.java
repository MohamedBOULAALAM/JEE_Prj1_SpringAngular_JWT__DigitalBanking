package tech.mobl3lm.digitalbanking.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDTO {
    private long totalAccounts;
    private long activeCustomers;
    private double totalBalance;
    private long totalOperations;
    private long totalDebits;
    private long totalCredits;
}
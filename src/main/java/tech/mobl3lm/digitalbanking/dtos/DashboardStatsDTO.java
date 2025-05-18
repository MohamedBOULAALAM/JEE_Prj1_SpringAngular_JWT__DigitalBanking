package tech.mobl3lm.digitalbanking.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsDTO {
    private double totalBalance;
    private double balanceChange;
    private long activeAccounts;
    private long newAccounts;
    private long recentTransactions;
    private long pendingTransactions;
}
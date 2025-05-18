package tech.mobl3lm.digitalbanking.servises;

import tech.mobl3lm.digitalbanking.dtos.AccountStatsDTO;
import tech.mobl3lm.digitalbanking.dtos.TransactionStatsDTO;
import java.util.List;

public interface DashboardService {
    tech.mobl3lm.digitalbanking.dtos.DashboardStatsDTO getDashboardStats();
    List<AccountStatsDTO> getAccountStats();
    TransactionStatsDTO getTransactionStats(String timeFrame);
}
package tech.mobl3lm.digitalbanking.web;

import lombok.RequiredArgsConstructor;
import tech.mobl3lm.digitalbanking.dtos.*;
import tech.mobl3lm.digitalbanking.servises.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public tech.mobl3lm.digitalbanking.dtos.DashboardStatsDTO getSummary() {
        return dashboardService.getDashboardStats();
    }

    @GetMapping("/account-stats")
    public List<AccountStatsDTO> getAccountStats() {
        return dashboardService.getAccountStats();
    }

    @GetMapping("/transaction-stats")
    public TransactionStatsDTO getTransactionStats(@RequestParam(defaultValue = "weekly") String timeFrame) {
        return dashboardService.getTransactionStats(timeFrame);
    }
}
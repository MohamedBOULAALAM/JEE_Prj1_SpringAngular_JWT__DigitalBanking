package tech.mobl3lm.digitalbanking.web;

import org.springframework.web.bind.annotation.*;
import tech.mobl3lm.digitalbanking.entities.Operation;
import tech.mobl3lm.digitalbanking.servises.CustomerService;
import java.util.List;

@RestController
@RequestMapping("/comptes")
public class OperationController {

    private final CustomerService customerService;

    public OperationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // POST /comptes/{id}/debit — effectuer un débit
    @PostMapping("/{id}/debit")
    public void debit(@PathVariable String id, @RequestParam double amount, @RequestParam String description) {
        customerService.debit(id, amount, description);
    }

    // POST /comptes/{id}/credit — effectuer un crédit
    @PostMapping("/{id}/credit")
    public void credit(@PathVariable String id, @RequestParam double amount, @RequestParam String description) {
        customerService.credit(id, amount, description);
    }

    // GET /comptes/{id}/operations — afficher l’historique des opérations d’un compte
    @GetMapping("/{id}/operations")
    public List<Operation> getOperations(@PathVariable String id) {
        return customerService.getAccountOperations(id);
    }
}
package tech.mobl3lm.digitalbanking.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mobl3lm.digitalbanking.dtos.*;
import tech.mobl3lm.digitalbanking.exceptions.*;
import tech.mobl3lm.digitalbanking.servises.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CustomerService customerService;

    public CompteController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /comptes — lister tous les comptes
    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(customerService.getAllAccounts());
    }

    // GET /comptes/{id} — afficher un compte spécifique
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getAccountById(@PathVariable String id) {
        try {
            BankAccountDTO account = customerService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /comptes — créer un compte (using DTO)
    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@RequestBody BankAccountRequestDTO accountDTO) {
        try {
            BankAccountDTO createdAccount = customerService.createAccount(accountDTO);
            return ResponseEntity.ok(createdAccount);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /comptes/{id} — modifier un compte
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateAccount(
            @PathVariable String id,
            @RequestBody BankAccountDTO bankAccountDTO) {
        try {
            BankAccountDTO updatedAccount = customerService.updateAccount(id, bankAccountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /comptes/{id} — supprimer un compte
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        try {
            customerService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
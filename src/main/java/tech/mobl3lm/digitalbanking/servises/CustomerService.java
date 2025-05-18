package tech.mobl3lm.digitalbanking.servises;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import tech.mobl3lm.digitalbanking.entities.Customer;
import tech.mobl3lm.digitalbanking.entities.Operation;
import tech.mobl3lm.digitalbanking.enums.OperationType;
import tech.mobl3lm.digitalbanking.exceptions.BankAccountNotFoundException;
import tech.mobl3lm.digitalbanking.exceptions.CustomerNotFoundException;
import tech.mobl3lm.digitalbanking.exceptions.InsufficientBalanceException;
import tech.mobl3lm.digitalbanking.repositories.AccountOperationRepository;
import tech.mobl3lm.digitalbanking.repositories.BankAccountRepository;
import tech.mobl3lm.digitalbanking.repositories.CustomerRepository;

import java.util.List;

@Transactional
@Service
public class CustomerService  implements CustomerServiceinterface{
    private final CustomerRepository customerRepo;
    private final BankAccountRepository bankAccountRepo;
    private final AccountOperationRepository operationRepo;

    public CustomerService(CustomerRepository customerRepo, BankAccountRepository bankAccountRepo, AccountOperationRepository operationRepo) {
        this.customerRepo = customerRepo;
        this.bankAccountRepo = bankAccountRepo;
        this.operationRepo = operationRepo;
    }

    public List<Customer> getAllClients() {
        try{
            return customerRepo.findAll();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Customer getClientById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public Customer createClient(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateClient(Long id, Customer customerDetails) {
        Customer existingCustomer = getClientById(id);
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhone(customerDetails.getPhone());
        existingCustomer.setAddress(customerDetails.getAddress());
        existingCustomer.setCity(customerDetails.getCity());
        existingCustomer.setPassword(customerDetails.getPassword());
        return customerRepo.save(existingCustomer);
    }

    public boolean deleteClient(Long id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccountRepo.findAll();
    }

    public BankAccount getAccount(String id) {
        return bankAccountRepo.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + id));

    }

    public BankAccount createAccountForCustomer(Long clientId, BankAccount account) {
        Customer customer = getClientById(clientId);
        account.setCustomer(customer);
        return bankAccountRepo.save(account);
    }

    public BankAccount updateAccount(String id, BankAccount updatedAccount) {
        BankAccount existing = getAccount(id);
        existing.setBalance(updatedAccount.getBalance());
        existing.setCurrency(updatedAccount.getCurrency());
        existing.setStatus(updatedAccount.getStatus());
        return bankAccountRepo.save(existing);
    }

    public boolean deleteAccount(String id) {
        Long accountId = Long.valueOf(id);
        if (bankAccountRepo.existsById(id)) {
            bankAccountRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public void debit(String id, double amount, String description) {
        BankAccount account = getAccount(id);
        if (account.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance for account ID: " + id);
        account.setBalance(account.getBalance() - amount);
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setOperationType(OperationType.DEBIT);
        operation.setBankAccount(account);
        operationRepo.save(operation);
        bankAccountRepo.save(account);
    }

    public void credit(String id, double amount, String description) {
        BankAccount account = getAccount(id);
        account.setBalance(account.getBalance() + amount);
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setOperationType(OperationType.CREDIT);
        operation.setBankAccount(account);
        operationRepo.save(operation);
        bankAccountRepo.save(account);
    }

    public List<Operation> getAccountOperations(String id) {
        BankAccount account = getAccount(id);
        List<Operation> operations = operationRepo.findByBankAccount(account);
        if (operations.isEmpty()) {
            throw new BankAccountNotFoundException("Account not found with id: " + id);
        }
        return operations;
    }
}
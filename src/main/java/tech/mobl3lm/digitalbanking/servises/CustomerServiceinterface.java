package tech.mobl3lm.digitalbanking.servises;


import tech.mobl3lm.digitalbanking.entities.BankAccount;
import tech.mobl3lm.digitalbanking.entities.Customer;
import tech.mobl3lm.digitalbanking.entities.Operation;
import java.util.List;

public interface CustomerServiceinterface {

    // Customer management
    List<Customer> getAllClients();
    Customer getClientById(Long id);
    Customer createClient(Customer customer);
    Customer updateClient(Long id, Customer customerDetails);
    boolean deleteClient(Long id);

    // Bank account management
    List<BankAccount> getAllAccounts();
    BankAccount getAccount(String id);
    BankAccount createAccountForCustomer(Long clientId, BankAccount account);
    BankAccount updateAccount(String id, BankAccount updatedAccount);
    boolean deleteAccount(String id);

    // Operations
    void debit(String id, double amount, String description);
    void credit(String id, double amount, String description);
    List<Operation> getAccountOperations(String id);
}
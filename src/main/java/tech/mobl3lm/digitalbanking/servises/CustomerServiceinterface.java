package tech.mobl3lm.digitalbanking.servises;


import tech.mobl3lm.digitalbanking.dtos.*;
import tech.mobl3lm.digitalbanking.entities.BankAccount;
import tech.mobl3lm.digitalbanking.entities.Customer;
import tech.mobl3lm.digitalbanking.entities.Operation;
import java.util.List;

public interface CustomerServiceinterface {

    // Customer management
    List<CustomerDTO> getAllClients();
    CustomerDTO getClientById(String id);
    CustomerDTO createClient(CustomerDTO customerDTO);
    CustomerDTO updateClient(String id, CustomerDTO customerDTO);
    boolean deleteClient(String id);

    // Bank account management
    List<BankAccountDTO> getAllAccounts();
    BankAccountDTO getAccount(String id);
    BankAccountDTO createAccount(BankAccountRequestDTO accountDTO);
    BankAccountDTO updateAccount(String id, BankAccountDTO bankAccountDTO);
    boolean deleteAccount(String id);

    // Operations
    void debit(String accountId, CreditDebitRequestDTO requestDTO);
    void credit(String accountId, CreditDebitRequestDTO requestDTO);
    void transfer(TransferRequestDTO transferRequestDTO);
    List<AccountOperationDTO> getAccountOperations(String accountId);
}
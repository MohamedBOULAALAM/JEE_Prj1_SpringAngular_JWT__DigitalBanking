package tech.mobl3lm.digitalbanking.servises;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import tech.mobl3lm.digitalbanking.enums.*;
import tech.mobl3lm.digitalbanking.exceptions.*;
import tech.mobl3lm.digitalbanking.entities.*;
import tech.mobl3lm.digitalbanking.mappers.BankAccountMapper;
import tech.mobl3lm.digitalbanking.repositories.*;
import tech.mobl3lm.digitalbanking.dtos.*;

@Transactional
@Service
public class CustomerService implements CustomerServiceinterface {

    private final CustomerRepository customerRepo;
    private final BankAccountRepository bankAccountRepo;
    private final AccountOperationRepository operationRepo;
    private final BankAccountMapper bankAccountMapper;

    public CustomerService(CustomerRepository customerRepo,
                           BankAccountRepository bankAccountRepo,
                           AccountOperationRepository operationRepo,
                           BankAccountMapper bankAccountMapper) {
        this.customerRepo = customerRepo;
        this.bankAccountRepo = bankAccountRepo;
        this.operationRepo = operationRepo;
        this.bankAccountMapper = bankAccountMapper;
    }

    // Customer management methods
    @Override
    public List<CustomerDTO> getAllClients() {
        return customerRepo.findAll().stream()
                .map(bankAccountMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getClientById(String id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO createClient(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO updateClient(String id, CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());
        customer.setPassword(customerDTO.getPassword());

        Customer updatedCustomer = customerRepo.save(customer);
        return bankAccountMapper.fromCustomer(updatedCustomer);
    }

    @Override
    public boolean deleteClient(String id) {
        if (!customerRepo.existsById(id)) {
            return false;
        }
        customerRepo.deleteById(id);
        return true;
    }

    // Bank account management methods
    @Override
    public List<BankAccountDTO> getAllAccounts() {
        return bankAccountRepo.findAll().stream()
                .map(account -> {
                    if (account instanceof SavingAccount) {
                        return bankAccountMapper.fromSavingAccount((SavingAccount) account);
                    } else {
                        return bankAccountMapper.fromCurrentAccount((CurrentAccount) account);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getAccount(String id) {  // Changed from String to Long
        BankAccount account = bankAccountRepo.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + id));

        if (account instanceof SavingAccount) {
            return bankAccountMapper.fromSavingAccount((SavingAccount) account);
        } else {
            return bankAccountMapper.fromCurrentAccount((CurrentAccount) account);
        }
    }

    @Override
    public BankAccountDTO createAccount(BankAccountRequestDTO accountDTO) {
        Customer customer = customerRepo.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + accountDTO.getCustomerId()));

        BankAccount account;
        if (accountDTO.getType().equals("SAV")) {
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setInterestRate(accountDTO.getInterestRate());
            account = savingAccount;
        } else {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setOverDraft(accountDTO.getOverdraft());
            account = currentAccount;
        }

        account.setBalance(accountDTO.getInitialBalance());
        account.setCustomer(customer);
        account.setCreatedAt(new Date());
        account.setStatus(AccountStatus.ACTIVE);

        BankAccount savedAccount = bankAccountRepo.save(account);
        return bankAccountMapper.fromBankAccount(savedAccount);
    }

    @Override
    public BankAccountDTO updateAccount(String id, BankAccountDTO bankAccountDTO) {  // Changed from String to Long
        BankAccount account = bankAccountRepo.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + id));

        account.setBalance(bankAccountDTO.getBalance());
        account.setStatus(AccountStatus.valueOf(bankAccountDTO.getStatus()));

        if (account instanceof SavingAccount savingAccount && bankAccountDTO instanceof SavingAccountDTO savingAccountDTO) {
            savingAccount.setInterestRate(savingAccountDTO.getInterestRate());
        } else if (account instanceof CurrentAccount currentAccount && bankAccountDTO instanceof CurrentAccountDTO currentAccountDTO) {
            currentAccount.setOverDraft(currentAccountDTO.getOverdraft());
        }

        BankAccount updatedAccount = bankAccountRepo.save(account);
        return bankAccountMapper.fromBankAccount(updatedAccount);
    }


    @Override
    public boolean deleteAccount(String id) {  // Changed from String to Long
        if (!bankAccountRepo.existsById(id)) {
            return false;
        }

        // First delete all operations associated with this account
        operationRepo.deleteByBankAccountId(id);

        // Then delete the account
        bankAccountRepo.deleteById(id);
        return true;
    }

    // Operation methods
    @Override
    public void debit(String accountId, CreditDebitRequestDTO requestDTO) {
        if (requestDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }

        BankAccount account = bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + accountId));

        // Check balance based on account type
        if (account instanceof CurrentAccount currentAccount) {
            if (currentAccount.getBalance() + currentAccount.getOverDraft() < requestDTO.getAmount()) {
                throw new InsufficientBalanceException("Insufficient balance including overdraft");
            }
        } else if (account.getBalance() < requestDTO.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Perform debit
        account.setBalance(account.getBalance() - requestDTO.getAmount());

        // Record operation
        Operation operation = new Operation();
        operation.setOperationType(OperationType.DEBIT);
        operation.setAmount(requestDTO.getAmount());
        operation.setDescription(requestDTO.getDescription());
        operation.setBankAccount(account);
        operation.setOperationDate(new Date());

        operationRepo.save(operation);
        bankAccountRepo.save(account);
    }

    @Override
    public void credit(String accountId, CreditDebitRequestDTO requestDTO) {
        if (requestDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }

        BankAccount account = bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + accountId));

        // Perform credit
        account.setBalance(account.getBalance() + requestDTO.getAmount());

        // Record operation
        Operation operation = new Operation();
        operation.setOperationType(OperationType.CREDIT);
        operation.setAmount(requestDTO.getAmount());
        operation.setDescription(requestDTO.getDescription());
        operation.setBankAccount(account);
        operation.setOperationDate(new Date());

        operationRepo.save(operation);
        bankAccountRepo.save(account);
    }

    @Override
    public void transfer(TransferRequestDTO transferRequestDTO) {
        // Convert String account IDs to Long if needed
        String sourceAccountId = transferRequestDTO.getAccountSource();
        String destAccountId = transferRequestDTO.getAccountDestination();

        // Create debit request
        CreditDebitRequestDTO debitRequest = new CreditDebitRequestDTO();
        debitRequest.setAccountId(sourceAccountId);
        debitRequest.setAmount(transferRequestDTO.getAmount());
        debitRequest.setDescription(transferRequestDTO.getDescription());

        // Create credit request
        CreditDebitRequestDTO creditRequest = new CreditDebitRequestDTO();
        creditRequest.setAccountId(destAccountId);
        creditRequest.setAmount(transferRequestDTO.getAmount());
        creditRequest.setDescription(transferRequestDTO.getDescription());

        // Execute operations
        debit(sourceAccountId, debitRequest);
        credit(destAccountId, creditRequest);
    }

    @Override
    public List<AccountOperationDTO> getAccountOperations(String accountId) {  // Changed from String to Long
        BankAccount account = bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found with id: " + accountId));

        return operationRepo.findByBankAccountId(accountId).stream()
                .map(operation -> bankAccountMapper.fromOperation((Operation) operation))
                .collect(Collectors.toList());
    }
}
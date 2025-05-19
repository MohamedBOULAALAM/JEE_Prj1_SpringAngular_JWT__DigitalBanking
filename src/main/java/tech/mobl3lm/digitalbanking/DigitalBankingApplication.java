package tech.mobl3lm.digitalbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import tech.mobl3lm.digitalbanking.entities.*;
import tech.mobl3lm.digitalbanking.enums.AccountStatus;
import tech.mobl3lm.digitalbanking.repositories.AccountOperationRepository;
import tech.mobl3lm.digitalbanking.repositories.BankAccountRepository;
import tech.mobl3lm.digitalbanking.repositories.CustomerRepository;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }



    public CommandLineRunner commandLineRunner(CustomerRepository customerRepo, BankAccountRepository bankAccountRepo, AccountOperationRepository operationRepo) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                Customer customer = Customer.builder()
                        .phone("05552638"+"121"+i)
                        .email(i+"MedB@mhmd.n7")
                        .city("Zazat"+i)
                        .name("MohamedB"+i)
                        .build();
                customerRepo.save(customer);
                if(i%2==0)
                {
                    SavingAccount savingAccount = SavingAccount.builder()
                            .currency("MAD")
                            .status(AccountStatus.ACTIVE)
                            .customer(customer)
                            .interestRate(2.5 * i)
                            .balance(Math.random() * 10000)
                            .build();
                    bankAccountRepo.save(savingAccount);


                }
                else{
                    CurrentAccount currentAccount = CurrentAccount.builder()
                            .currency("MAD")
                            .status(AccountStatus.ACTIVE)
                            .customer(customer)
                            .overDraft(5000.0 * i)
                            .balance(Math.random() * 10000)
                            .build();
                    bankAccountRepo.save(currentAccount);
                }


            }

        };
    }
}
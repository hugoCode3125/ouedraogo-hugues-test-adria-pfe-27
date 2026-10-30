package ma.enset.accountservice;

import ma.enset.accountservice.domain.Account;
import ma.enset.accountservice.domain.AccountStatus;
import ma.enset.accountservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.create(Account.builder()
                    .customerReference("Hugues")
                    .status(AccountStatus.ACTIVE)
                    .currency("EUR")
                    .balance(1000)
                    .build());

            accountService.create(Account.builder()
                    .customerReference("Fawzi")
                    .status(AccountStatus.ACTIVE)
                    .currency("FCFA")
                    .balance(20000)
                    .build());

            accountService.create(Account.builder()
                    .customerReference("Djelika")
                    .status(AccountStatus.ACTIVE)
                    .currency("MAD")
                    .balance(5880)
                    .build());
        };
    }

}

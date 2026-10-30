package ma.enset.transactionservice;

import ma.enset.transactionservice.domain.Transaction;
import ma.enset.transactionservice.mcp.AccountRestClient;
import ma.enset.transactionservice.model.Account;
import ma.enset.transactionservice.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(TransactionService service,
							AccountRestClient accountClient) {

		return args -> {

			List<Account> accounts = accountClient.getAllAccounts();

			for (int i = 0; i < 5; i++) {

				Account account = accounts.get(i % accounts.size());

				service.create(Transaction.builder()
						.accountId(account.getId())
						.amount((100 + i * 20))
						.currency(account.getCurrency())
						.merchantCategory("FOOD")
						.country("MAR")
						.createdAt(Instant.now())
						.build());
			}
		};
	}
}

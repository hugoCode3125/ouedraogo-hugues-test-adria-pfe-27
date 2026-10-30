package ma.enset.transactionservice.mcp;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.enset.transactionservice.model.Account;
import ma.enset.transactionservice.model.AccountStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountRestClient {
    @GetMapping("/accounts/{id}")
    @CircuitBreaker(name = "customer-breaker",fallbackMethod = "getCustomerDefault")
    Account getAccount(@PathVariable UUID id);

    default Account getCustomerDefault( UUID id, Exception e) {
        return Account.builder()
                .id(UUID.randomUUID())
                .balance(0)
                .status(AccountStatus.BLOCKED)
                .build();
    }

    @GetMapping("/accounts")
    List<Account> getAllAccounts();

}

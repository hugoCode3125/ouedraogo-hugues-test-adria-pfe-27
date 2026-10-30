package ma.enset.transactionservice.service;

import jakarta.transaction.Transactional;
import ma.enset.transactionservice.domain.Transaction;
import ma.enset.transactionservice.mcp.AccountRestClient;
import ma.enset.transactionservice.model.Account;
import ma.enset.transactionservice.model.AccountStatus;
import ma.enset.transactionservice.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountRestClient accountClient;

    public TransactionService(TransactionRepository repository, AccountRestClient accountClient) {
        this.repository = repository;
        this.accountClient = accountClient;
    }

    @Transactional
    public Transaction create(Transaction transaction) {

        Account account =
                accountClient.getAccount(transaction.getAccountId());

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account is blocked");
        }

        if (!account.getCurrency().equals(transaction.getCurrency())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Currency mismatch");
        }

        if (transaction.getAmount()<= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid amount");
        }

        double amount = transaction.getAmount();

        if (account.getBalance() < amount) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        transaction.setCreatedAt(Instant.now());
        Transaction saved = repository.save(transaction);


        return saved;
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public List<Transaction> getByAccount(UUID accountId) {
        return repository.findByAccountId(accountId);
    }
}

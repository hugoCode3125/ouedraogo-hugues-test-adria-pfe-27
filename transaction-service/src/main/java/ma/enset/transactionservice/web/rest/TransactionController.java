package ma.enset.transactionservice.web.rest;

import ma.enset.transactionservice.domain.Transaction;
import ma.enset.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.create(transaction);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getByAccount(
            @PathVariable UUID accountId) {
        return service.getByAccount(accountId);
    }
}

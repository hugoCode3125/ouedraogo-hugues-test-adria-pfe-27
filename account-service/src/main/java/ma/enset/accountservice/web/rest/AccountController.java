package ma.enset.accountservice.web.rest;

import ma.enset.accountservice.domain.Account;
import ma.enset.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/accounts")
    public Account create(@RequestBody Account account) {
        return service.create(account);
    }

    @GetMapping("accounts/{id}")
    public Account getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/accounts")
    public List<Account> getAll() {
        return service.getAll();
    }


}

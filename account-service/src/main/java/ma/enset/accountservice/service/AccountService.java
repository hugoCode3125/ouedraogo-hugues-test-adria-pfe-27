package ma.enset.accountservice.service;

import jakarta.transaction.Transactional;
import ma.enset.accountservice.domain.Account;
import ma.enset.accountservice.domain.AccountStatus;
import ma.enset.accountservice.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

        private final AccountRepository repository;

        public AccountService(AccountRepository repository) {
            this.repository = repository;
        }

        public Account create(Account account) {
            account.setStatus(AccountStatus.ACTIVE);
            account.setBalance(0);
            return repository.save(account);
        }

        public Account getById(UUID id) {
            return repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Account not found"));
        }

        public List<Account> getAll() {
            return repository.findAll();
        }


    }


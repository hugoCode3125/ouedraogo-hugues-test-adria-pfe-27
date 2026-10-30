package ma.enset.transactionservice.repository;

import ma.enset.transactionservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(UUID accountId);
}

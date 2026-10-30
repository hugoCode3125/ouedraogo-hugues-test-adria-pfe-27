package ma.enset.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Account {
    private UUID id;
    private String customerReference;
    private AccountStatus status;
    private String currency;
    private double balance;
}

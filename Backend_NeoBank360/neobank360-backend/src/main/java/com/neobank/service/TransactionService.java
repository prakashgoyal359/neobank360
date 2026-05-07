
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neobank.entity.Account;
import com.neobank.entity.Transaction;
import com.neobank.repository.AccountRepository;
import com.neobank.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accRepo;
    @Autowired 
    private TransactionRepository txnRepo;

    public String transfer(Long from, Long to, double amt){

        Account a = accRepo.findById(from).get();
        Account b = accRepo.findById(to).get();

        if(a.getBalance() < amt) throw new RuntimeException("Insufficient");

        a.setBalance(a.getBalance()-amt);
        b.setBalance(b.getBalance()+amt);

        accRepo.save(a);
        accRepo.save(b);

        Transaction t = new Transaction();
        t.setAccountId(from);
        t.setAmount(amt);
        t.setType("DEBIT");
        t.setTransactionDate(java.time.LocalDateTime.now());

        txnRepo.save(t);

        return "Success";
    }
}
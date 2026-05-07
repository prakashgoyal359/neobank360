import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.neobank.entity.Bill;
import com.neobank.repository.BillRepository;

@Service
public class BillService {

    @Autowired private BillRepository repo;

    public Bill create(Bill b){

        if(b.getDueDate().isBefore(java.time.LocalDate.now()))
            throw new RuntimeException("Past date");

        b.setStatus("PENDING");
        return repo.save(b);
    }
}
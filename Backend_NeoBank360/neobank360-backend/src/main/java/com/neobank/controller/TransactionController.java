import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.service.TransactionService;

@RestController
@RequestMapping("/api/tx")
public class TransactionController {

    @Autowired private TransactionService service;

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from,
                           @RequestParam Long to,
                           @RequestParam double amount){
        return service.transfer(from,to,amount);
    }
}
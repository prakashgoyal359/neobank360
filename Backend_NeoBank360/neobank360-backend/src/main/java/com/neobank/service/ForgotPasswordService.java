import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neobank.entity.Otp;
import com.neobank.entity.User;
import com.neobank.repository.OtpRepository;
import com.neobank.repository.UserRepository;

@Service
public class ForgotPasswordService {

    @Autowired private OtpRepository otpRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    public void sendOtp(String email){

        String otp = ""+(new java.util.Random().nextInt(900000)+100000);

        Otp o = new Otp();
        o.setEmail(email);
        o.setOtp(otp);
        o.setExpiry(java.time.LocalDateTime.now().plusMinutes(5));

        otpRepo.save(o);

        System.out.println("OTP: "+otp);
    }

    public void reset(String email,String otp,String pass){

        Otp o = otpRepo.findByEmail(email);

        if(!o.getOtp().equals(otp))
            throw new RuntimeException("Invalid OTP");

        User u = userRepo.findByEmail(email);
        u.setPassword(encoder.encode(pass));

        userRepo.save(u);
    }
}

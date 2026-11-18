package com.yatima.config;
import com.yatima.model.User;
import com.yatima.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component
public class AdminSeeder implements CommandLineRunner {
  private final UserRepository repo;
  private final PasswordEncoder encoder;
  public AdminSeeder(UserRepository r, PasswordEncoder e){repo=r;encoder=e;}
  @Override
  public void run(String... args){
    // KHADIJA - MMILIKI - HAJISAJILI - ASHAKUWA
    if(repo.findByUsername("khadija").isEmpty()){
      User u=new User();
      u.setUsername("khadija");
      u.setEmail("khadija@yatimaumra.or.tz");
      u.setPhone("0777123456");
      u.setPassword(encoder.encode("Khadija@2026"));
      u.setRole("SUPER_ADMIN");
      u.setTenantId("KHADIJA");
      u.setOrg("Yatima Umra");
      u.setPlan("KHADIJA-150000");
      u.setAmount(0.0);
      u.setStatus("active");
      u.setPaymentMethod("PBZ");
      u.setPbzAccount("0887387001");
      u.setPbzName("KHADIJA DENGE");
      u.setMyPbzAccount("0887387001");
      u.setMyPbzName("KHADIJA DENGE");
      u.setRequirements("Mmiliki wa Mfumo");
      u.setApprovedByKhadija(true);
      u.setExpiresAt(LocalDateTime.now().plusYears(10));
      repo.save(u);
      System.out.println("✅ MMILIKI KHADIJA AMEUNDWA: username=khadija / password=Khadija@2026");
    }
  }
}

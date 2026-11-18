package com.yatima.config;
import com.yatima.model.Package;
import com.yatima.model.User;
import com.yatima.repository.PackageRepository;
import com.yatima.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component
public class AdminSeeder implements CommandLineRunner {
  private final UserRepository repo;
  private final PackageRepository packageRepo;
  private final PasswordEncoder encoder;
  public AdminSeeder(UserRepository r, PackageRepository p, PasswordEncoder e){repo=r;packageRepo=p;encoder=e;}

  @Override
  public void run(String... args){
    seedKhadija();
    seedDemo();
    seedPackages();
  }

  private void seedKhadija(){
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
    } else {
      User u = repo.findByUsername("khadija").get();
      boolean changed = false;
      if (!"active".equalsIgnoreCase(u.getStatus())) { u.setStatus("active"); changed = true; }
      
            if (Boolean.TRUE.equals(u.getLocked())) { u.setLocked(false); changed = true; }      
            if (u.getExpiresAt() == null || u.getExpiresAt().isBefore(LocalDateTime.now())) { u.setExpiresAt(LocalDateTime.now().plusYears(10)); changed = true; }
      if (changed) { repo.save(u); System.out.println("✅ KHADIJA AMEFUNGULIWA TENA"); }
    }
  }

  // --- 2. DEMO USER - kwa kujaribu bila kulipa ---
  private void seedDemo(){
    if(repo.findByUsername("demo").isEmpty()){
      User u=new User();
      u.setUsername("demo");
      u.setEmail("demo@yatimaumra.or.tz");
      u.setPhone("0777000000");
      u.setPassword(encoder.encode("Demo@2026"));
      u.setRole("TENANT_ADMIN");
      u.setTenantId("demo");
      u.setOrg("Demo Taasisi");
      u.setPlan("FREE-DEMO");
      u.setAmount(0.0);
      u.setStatus("active");
      u.setPaymentMethod("PBZ");
      u.setPbzAccount("0887387001");
      u.setPbzName("KHADIJA DENGE");
      u.setRequirements("Akaunti ya kujaribu");
      u.setApprovedByKhadija(true);
      u.setExpiresAt(LocalDateTime.now().plusDays(3));
      repo.save(u);
      System.out.println("✅ DEMO AMEUNDWA: username=demo / password=Demo@2026 / siku 3");
    }
  }

  // --- 3. PACKAGES 4 - FREE / KIDOGO / KAWAIDA / KUBWA ---
  private void seedPackages(){
    createPackageIfMissing("FREE-DEMO", "FREE", 0.0, 3, 10, 5, 1, false,
      "Jaribu bure siku 3. Yatima hadi 10, Umra hadi 5.");
    createPackageIfMissing("KIDOGO-50000", "KIDOGO", 50000.0, 30, 50, 20, 2, false,
      "Kwa kuanza. Yatima hadi 50, Ripoti ya mwezi, Msaada WhatsApp, Cheti.");
    createPackageIfMissing("KAWAIDA-100000", "KAWAIDA", 100000.0, 30, 200, 100, 3, true,
      "Maarufu zaidi. Yatima hadi 200, Umra hadi 100, Ripoti kamili, Msaada wa haraka.");
    createPackageIfMissing("KUBWA-200000", "KUBWA", 200000.0, 365, 9999, 9999, 4, false,
      "Kwa taasisi kubwa. Yatima bila kikomo, Umra bila kikomo, Msaada wa kipekee.");
  }

  private void createPackageIfMissing(String code, String name, Double price, Integer days,
      Integer maxOrphans, Integer maxUmra, Integer orderNo, Boolean popular, String desc){
    if (packageRepo.findByCode(code).isPresent()) return;
    Package p = new Package();
    p.setCode(code);
    p.setName(name);
    p.setDisplayName(name);
    p.setPrice(price);
    p.setBei(price);
    p.setDays(days);
    p.setDescription(desc);
    p.setMaelezo(desc);
    p.setMaxOrphans(maxOrphans);
    p.setMaxUmra(maxUmra);
    p.setMaxTasisi(1);
    p.setMaxBookings(9999);
    p.setOrderNo(orderNo);
    p.setIsPopular(popular);
    p.setActive(true);
    p.setStatus("ACTIVE");
    p.setCurrency("TZS");
    p.setPbzAccount("0887387001");
    p.setPbzName("KHADIJA DENGE");
    packageRepo.save(p);
    System.out.println("✅ PACKAGE IMEUNDWA: " + code);
  }
}

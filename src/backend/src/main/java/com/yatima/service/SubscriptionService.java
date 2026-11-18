package com.yatima.service;
import com.yatima.model.Package;
import com.yatima.model.Payment;
import com.yatima.model.User;
import com.yatima.repository.PackageRepository;
import com.yatima.repository.PaymentRepository;
import com.yatima.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SubscriptionService {
  private final UserRepository userRepo;
  private final PaymentRepository payRepo;
  private final PackageRepository pkgRepo;

  public SubscriptionService(UserRepository u, PaymentRepository p, PackageRepository k){
    userRepo=u; payRepo=p; pkgRepo=k;
  }

  public void activateAfterPayment(User u, String planCode, Payment pay){
    String code = (planCode==null||planCode.isEmpty()) ? (pay!=null?pay.getPlan():u.getPlan()) : planCode;
    Package pkg = pkgRepo.findByCode(code).orElse(null);
    int days = pkg!=null ? pkg.getDays() : 30;
    Double price = pkg!=null ? pkg.getPrice() : 0.0;
    LocalDateTime base = (u.getExpiresAt()!=null && u.getExpiresAt().isAfter(LocalDateTime.now())) ? u.getExpiresAt() : LocalDateTime.now();
    u.setPlan(code);
    u.setStatus("active");
    u.setLocked(false);
    u.setApprovedByKhadija(true);
    u.setExpiresAt(base.plusDays(days));
    if(pay!=null && pay.getAmount()!=null) u.setAmount(pay.getAmount());
    else u.setAmount(price);
    userRepo.save(u);
    if(pay!=null){ pay.approve("KHADIJA", u.getExpiresAt()); payRepo.save(pay); }
  }

  public void changePlan(User u, String planCode){
    Package pkg = pkgRepo.findByCode(planCode).orElse(null);
    u.setPlan(planCode);
    if(pkg!=null) u.setAmount(pkg.getPrice());
    userRepo.save(u);
  }

  public static Map<String,Object> planInfo(String plan){
    if(plan==null) plan="";
    if(plan.contains("KIDOGO")) return Map.of("price",50000.0,"days",30);
    if(plan.contains("KAWAIDA")) return Map.of("price",100000.0,"days",30);
    if(plan.contains("KUBWA")) return Map.of("price",200000.0,"days",365);
    if(plan.contains("KHADIJA")) return Map.of("price",150000.0,"days",30);
    return Map.of("price",0.0,"days",3);
  }
}

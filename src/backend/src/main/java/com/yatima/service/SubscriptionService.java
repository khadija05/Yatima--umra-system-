package com.yatima.service;
import com.yatima.model.User;
import com.yatima.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class SubscriptionService {
  public static final String PBZ_ACCOUNT = "0887387001";
  public static final String PBZ_NAME = "KHADIJA DENGE";
  private final UserRepository userRepo;
  public SubscriptionService(UserRepository r) { this.userRepo = r; }
  @Scheduled(fixedRate = 60000)
  public void checkExpired() {
    for (User u : userRepo.findAll()) {
      if ("active".equals(u.getStatus()) && u.getExpiresAt() != null && u.getExpiresAt().isBefore(LocalDateTime.now())) {
        u.setStatus("expired"); userRepo.save(u);
        sendWhatsApp(u.getPhone(), "Habari "+u.getUsername()+"! Plan yako ya "+u.getPlan()+" imeisha. Lipa "+u.getAmount()+" TZS kupitia PBZ "+PBZ_ACCOUNT+" - "+PBZ_NAME+" ili mfumo ufunguke.");
      }
    }
  }
  public static void sendWhatsApp(String phone, String msg) {
    String clean = phone == null ? "" : phone.replaceFirst("^0","255");
    System.out.println("[WHATSAPP] To "+phone+" : "+msg+" | https://wa.me/"+clean);
  }
  public static int daysForPlan(String plan) { if (plan != null && plan.startsWith("FREE")) return 3; return 30; }
  public static double amountForPlan(String plan) {
    if (plan == null) return 0;
    if (plan.startsWith("KIDOGO")) return 50000;
    if (plan.startsWith("KAWAIDA")) return 100000;
    if (plan.startsWith("KUBWA")) return 200000;
    if (plan.startsWith("KHADIJA")) return 150000;
    return 0;
  }
}

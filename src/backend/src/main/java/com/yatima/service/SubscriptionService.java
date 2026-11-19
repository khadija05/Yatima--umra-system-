
package com.yatima.service;
import com.yatima.model.Payment;
import com.yatima.model.User;
import com.yatima.repository.PaymentRepository;
import com.yatima.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class SubscriptionService {
private final UserRepository users;
private final PaymentRepository pays;
public SubscriptionService(UserRepository users, PaymentRepository pays){ this.users = users; this.pays = pays; }
public void activateAfterPayment(User u, String plan, Payment p){
if(plan != null && !plan.isEmpty()) u.setPlan(plan);
u.setStatus("active");
u.setLocked(false);
u.setApprovedByKhadija(true);
LocalDateTime base = (u.getExpiresAt()!=null && u.getExpiresAt().isAfter(LocalDateTime.now())) ? u.getExpiresAt() : LocalDateTime.now();
int days = 30;
if(plan != null){
String pl = plan.toLowerCase();
if(pl.contains("year") || pl.contains("mwaka") || pl.contains("12")) days = 365;
else if(pl.contains("90") || pl.contains("3 month")) days = 90;
else if(pl.contains("7") || pl.contains("week")) days = 7;
}
u.setExpiresAt(base.plusDays(days));
users.save(u);
if(p != null){ p.approve("KHADIJA", u.getExpiresAt()); pays.save(p); }
}
public void changePlan(User u, String plan){
u.setPlan(plan);
users.save(u);
}
public boolean isActive(User u){
if(u==null) return false;
if(Boolean.TRUE.equals(u.getLocked())) return false;
if(u.getExpiresAt()==null) return true;
return u.getExpiresAt().isAfter(LocalDateTime.now());
}
public boolean hasActiveSubscription(String username){ return users.findByUsername(username).map(this::isActive).orElse(false); }
}

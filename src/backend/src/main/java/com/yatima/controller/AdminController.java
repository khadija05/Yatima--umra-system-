package com.yatima.controller;
import com.yatima.model.Payment;
import com.yatima.model.User;
import com.yatima.repository.PaymentRepository;
import com.yatima.repository.UserRepository;
import com.yatima.service.SubscriptionService;
import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/admin") @CrossOrigin("*")
public class AdminController {
  private final UserRepository userRepo; private final PaymentRepository payRepo; private final JwtUtil jwt;
  private final SubscriptionService subs;
  public AdminController(UserRepository u, PaymentRepository p, JwtUtil j, SubscriptionService s){userRepo=u;payRepo=p;jwt=j;subs=s;}

  private String role(String a){try{return (String)jwt.parse(a).get("role");}catch(Exception e){return "";}}
  private String user(String a){try{return jwt.parse(a).getSubject();}catch(Exception e){return "";}}
  private boolean isSuper(String a){return "SUPER_ADMIN".equals(role(a)) || "Admin".equals(role(a)) || "KHADIJA".equals(role(a));}
  private boolean isTenantAdmin(String a){return "TENANT_ADMIN".equals(role(a));}
  private boolean canManage(String a){return isSuper(a) || isTenantAdmin(a);}

  private boolean canSeeUser(String auth, User target){
    if (isSuper(auth)) return true;
    if (isTenantAdmin(auth)) return user(auth).equals(target.getTenantId()) || target.getTenantId().equals("") || target.getTenantId().equals(user(auth));
    return false;
  }

  private User safe(User u){ u.setPassword(null); u.setResetToken(null); return u; }

  // --- 1. Orodha ya watumiaji ---
  @GetMapping("/users") public ResponseEntity<?> users(@RequestHeader(value="Authorization",required=false) String auth){
    if(isSuper(auth)){ List<User> l=userRepo.findAll(); l.forEach(this::safe); return ResponseEntity.ok(l); }
    if(isTenantAdmin(auth)){ List<User> l=userRepo.findByTenantId(user(auth)); l.forEach(this::safe); return ResponseEntity.ok(l); }
    return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
  }

  // --- 2. Mtu mmoja ---
  @GetMapping("/users/{username}") public ResponseEntity<?> oneUser(@PathVariable String username, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    if(!canSeeUser(auth, o.get())) return ResponseEntity.status(403).body(Map.of("message","Si wa tenant yako"));
    return ResponseEntity.ok(safe(o.get()));
  }

  // --- 3. Stats ---
  @GetMapping("/stats") public ResponseEntity<?> stats(@RequestHeader(value="Authorization",required=false) String auth){
    if(isSuper(auth)){
      long total=userRepo.count();
      long active=userRepo.findAll().stream().filter(u->"active".equalsIgnoreCase(u.getStatus())).count();
      long expired=userRepo.findAll().stream().filter(u->"expired".equalsIgnoreCase(u.getStatus())).count();
      long pending=userRepo.findAll().stream().filter(u->"pending".equalsIgnoreCase(u.getStatus())).count();
      double money=payRepo.findAll().stream().filter(Payment::isApproved).mapToDouble(p->p.getAmount()==null?0:p.getAmount()).sum();
      double pendingMoney=payRepo.findAll().stream().filter(Payment::isPending).mapToDouble(p->p.getAmount()==null?0:p.getAmount()).sum();
      return ResponseEntity.ok(Map.of("total",total,"active",active,"expired",expired,"pending",pending,"money",money,"pendingMoney",pendingMoney));
    }
    if(isTenantAdmin(auth)){
      List<User> mine=userRepo.findByTenantId(user(auth));
      return ResponseEntity.ok(Map.of("total",mine.size(),"active",mine.stream().filter(u->"active".equalsIgnoreCase(u.getStatus())).count(),"expired",mine.stream().filter(u->"expired".equalsIgnoreCase(u.getStatus())).count(),"pending",mine.stream().filter(u->"pending".equalsIgnoreCase(u.getStatus())).count(),"money",0,"pendingMoney",0));
    }
    return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
  }

  // --- 4. Malipo yote ---
  @GetMapping("/payments") public ResponseEntity<?> payments(@RequestHeader(value="Authorization",required=false) String auth, @RequestParam(required=false) String status){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    List<Payment> all = isSuper(auth) ? payRepo.findAll() : payRepo.findByTenantId(user(auth));
    if (status != null && !status.isEmpty()) all = all.stream().filter(p->status.equalsIgnoreCase(p.getStatus())).toList();
    return ResponseEntity.ok(all);
  }

  // --- 5. Thibitisha mtumiaji ---
  @PostMapping("/approve/{username}") public ResponseEntity<?> approve(@PathVariable String username,@RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    User u=o.get(); if(!canSeeUser(auth, u)) return ResponseEntity.status(403).body(Map.of("message","Si wa tenant yako"));
    Payment lastPay = payRepo.findByUsername(username).stream().filter(Payment::isPending).reduce((a,b)->b).orElse(null);
    subs.activateAfterPayment(u, u.getPlan(), lastPay);
    return ResponseEntity.ok(Map.of("message","Amethibitishwa na Khadija - Plan: "+u.getPlan()+" hadi "+u.getExpiresAt()));
  }

  // --- 6. Thibitisha malipo kwa ID ---
  @PostMapping("/payments/{id}/approve") public ResponseEntity<?> approvePayment(@PathVariable Long id, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<Payment> o=payRepo.findById(id); if(o.isEmpty()) return ResponseEntity.notFound().build();
    Payment p=o.get();
    Optional<User> uo=userRepo.findByUsername(p.getUsername());
    if(uo.isPresent()) subs.activateAfterPayment(uo.get(), p.getPlan(), p);
    else { p.approve("KHADIJA", LocalDateTime.now().plusDays(30)); payRepo.save(p); }
    return ResponseEntity.ok(Map.of("message","Malipo yamethibitishwa"));
  }

  @PostMapping("/payments/{id}/reject") public ResponseEntity<?> rejectPayment(@PathVariable Long id, @RequestBody(required=false) Map<String,String> body, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<Payment> o=payRepo.findById(id); if(o.isEmpty()) return ResponseEntity.notFound().build();
    Payment p=o.get(); p.reject(body != null ? body.getOrDefault("reason","Imekataliwa na Khadija") : "Imekataliwa na Khadija"); payRepo.save(p);
    return ResponseEntity.ok(Map.of("message","Malipo yamekataliwa"));
  }

  // --- 7. Kataa mtumiaji ---
  @PostMapping("/reject/{username}") public ResponseEntity<?> reject(@PathVariable String username, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    User u=o.get(); u.setStatus("rejected"); u.setLocked(true); userRepo.save(u);
    return ResponseEntity.ok(Map.of("message","Amekataliwa"));
  }

  // --- 8. Funga ---
  @PostMapping("/lock/{username}") public ResponseEntity<?> lock(@PathVariable String username,@RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    User u=o.get(); u.setStatus("expired"); u.setLocked(true); userRepo.save(u);
    return ResponseEntity.ok(Map.of("message","Amefungwa"));
  }

  // --- 9. Fungua tena ---
  @PostMapping("/unlock/{username}") public ResponseEntity<?> unlock(@PathVariable String username, @RequestParam(defaultValue="30") int days, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    User u=o.get(); u.setStatus("active"); u.setLocked(false); u.setApprovedByKhadija(true);
    LocalDateTime base = (u.getExpiresAt()!=null && u.getExpiresAt().isAfter(LocalDateTime.now())) ? u.getExpiresAt() : LocalDateTime.now();
    u.setExpiresAt(base.plusDays(days)); userRepo.save(u);
    return ResponseEntity.ok(Map.of("message","Amefunguliwa kwa siku "+days));
  }

  // --- 10. Ongeza siku ---
  @PostMapping("/extend/{username}") public ResponseEntity<?> extend(@PathVariable String username, @RequestParam(defaultValue="30") int days, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    User u=o.get();
    LocalDateTime base = (u.getExpiresAt()!=null && u.getExpiresAt().isAfter(LocalDateTime.now())) ? u.getExpiresAt() : LocalDateTime.now();
    u.setExpiresAt(base.plusDays(days)); userRepo.save(u);
    return ResponseEntity.ok(Map.of("message","Ameongezewa siku "+days+" hadi "+u.getExpiresAt()));
  }

  // --- 11. Badilisha Plan ---
  @PostMapping("/change-plan/{username}") public ResponseEntity<?> changePlan(@PathVariable String username, @RequestParam String plan, @RequestHeader(value="Authorization",required=false) String auth){
    if(!canManage(auth)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    subs.changePlan(o.get(), plan);
    return ResponseEntity.ok(Map.of("message","Plan imebadilishwa kuwa "+plan));
  }

  // --- 12. Futa mtumiaji ---
  @DeleteMapping("/users/{username}") public ResponseEntity<?> delete(@PathVariable String username, @RequestHeader(value="Authorization",required=false) String auth){
    if(!isSuper(auth)) return ResponseEntity.status(403).body(Map.of("message","Ni SUPER_ADMIN tu"));
    Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build();
    userRepo.delete(o.get());
    return ResponseEntity.ok(Map.of("message","Amefutwa"));
  }
}

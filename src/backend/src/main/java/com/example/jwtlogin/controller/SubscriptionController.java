package com.example.jwtlogin.controller;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import com.example.jwtlogin.security.services.UserDetailsImpl; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/subscription") @CrossOrigin(origins="*")
public class SubscriptionController {
  @Autowired RenewRequestRepository renewRepo; @Autowired UserRepository users; @Autowired DemoRequestRepository demoRepo;
  @PostMapping("/renew-request") public ResponseEntity<?> renew(@RequestBody Map<String,String> b){
    RenewRequest r=new RenewRequest(); r.setEmail(b.get("email").toLowerCase().trim()); r.setPayerName(b.get("payerName")); r.setTransactionRef(b.get("transactionRef")); r.setPlan(b.getOrDefault("plan","KUBWA")); r.setPaymentMethod(b.getOrDefault("paymentMethod","PBZ")); try{ r.setAmount(Double.parseDouble(b.getOrDefault("amount","200000"))); }catch(Exception e){ r.setAmount(200000.0); } renewRepo.save(r); return ResponseEntity.ok(Map.of("message","Ombi limetumwa","id",r.getId()));
  }
  @GetMapping("/pending") public List<RenewRequest> pending(){ return renewRepo.findAll(); }
  @PostMapping("/approve/{id}") public ResponseEntity<?> approve(@PathVariable Long id){
    RenewRequest r=renewRepo.findById(id).orElseThrow(); r.setStatus("APPROVED"); renewRepo.save(r);
    users.findByUsername(r.getEmail()).ifPresent(u->{ Plan p=Plan.fromCode(r.getPlan()); u.setPlan(p.name()); u.setPlanPrice(p.getPrice()); LocalDate base=u.getSubscriptionExpiry()!=null && u.getSubscriptionExpiry().isAfter(LocalDate.now())?u.getSubscriptionExpiry():LocalDate.now(); u.setSubscriptionExpiry(base.plusDays(p.getDays())); u.setSubscriptionActive(true); u.setLocked(false); u.setTotalPaid(u.getTotalPaid()+(int)r.getAmount()); users.save(u); });
    return ResponseEntity.ok(Map.of("message","Imekubaliwa"));
  }
  @PostMapping("/reject/{id}") public ResponseEntity<?> reject(@PathVariable Long id){ RenewRequest r=renewRepo.findById(id).orElseThrow(); r.setStatus("REJECTED"); renewRepo.save(r); return ResponseEntity.ok(Map.of("message","Imekataliwa")); }
  @GetMapping("/admin/users") public List<User> allUsers(){ return users.findAll(); }
  @PostMapping("/admin/lock/{id}") public ResponseEntity<?> lock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(!u.isLocked()); users.save(u); return ResponseEntity.ok(Map.of("message","Imebadilishwa")); }
  @GetMapping("/me") public ResponseEntity<?> me(Authentication a){ User u=((UserDetailsImpl)a.getPrincipal()).getUser(); return ResponseEntity.ok(Map.of("plan",u.getPlan(),"subscriptionExpiry",u.getSubscriptionExpiry().toString(),"subscriptionActive",u.isSubscriptionActive())); }
}

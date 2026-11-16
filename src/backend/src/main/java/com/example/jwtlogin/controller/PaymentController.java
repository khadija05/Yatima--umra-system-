package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.UserRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.Map;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/payments")
public class PaymentController { @Autowired UserRepository userRepo;
@PostMapping("/lipa") public ResponseEntity<?> lipa(@RequestBody Map<String,String> req, Authentication auth){
  User u=userRepo.findByUsername(auth.getName()).orElse(null); if(u==null) return ResponseEntity.notFound().build();
  String planCode=req.getOrDefault("plan", u.getPlan()); Plan plan=Plan.fromCode(planCode);
  int kiasi=Integer.parseInt(req.getOrDefault("kiasi", String.valueOf((int)plan.getPrice())));
  u.setTotalPaid(u.getTotalPaid()+kiasi); u.setPlan(plan.name()); u.setPlanPrice(plan.getPrice());
  u.setSubscriptionExpiry(LocalDate.now().plusDays(plan.getDays())); u.setSubscriptionActive(true); u.setLocked(false);
  if(plan==Plan.DEMO) u.setDemoExpiry(LocalDate.now().plusDays(3));
  userRepo.save(u);
  return ResponseEntity.ok("✅ Umelipa "+kiasi+" TZS! Plan: "+plan.getDisplayName()+" - Imefunguka! Mwisho: "+u.getSubscriptionExpiry());
}
@GetMapping("/status") public ResponseEntity<?> status(Authentication auth){ User u=userRepo.findByUsername(auth.getName()).orElse(null); if(u==null) return ResponseEntity.notFound().build(); if(u.isLocked()) return ResponseEntity.status(403).body("🔒 IMEFUNGWA! Plan: "+u.getPlan()+" - Mwisho: "+u.getSubscriptionExpiry()+" - Lipa "+u.getPlanPrice()+" ili kufunguliwa."); return ResponseEntity.ok(u); }
}

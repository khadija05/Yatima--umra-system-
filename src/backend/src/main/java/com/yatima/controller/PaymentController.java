package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/payments") @CrossOrigin("*")
public class PaymentController {
  @Autowired UserRepository users; @Autowired PaymentRepository pays;
  @PostMapping("/lipa") public ResponseEntity<?> lipa(@RequestBody Map<String,String> b, Authentication auth){
    User u=users.findByUsername(auth.getName()).orElse(null); if(u==null) return ResponseEntity.status(401).body("Login kwanza");
    String plan=b.get("plan"); double kiasi=Double.parseDouble(b.getOrDefault("kiasi","0"));
    Payment p=new Payment(); p.userId=u.id; p.plan=plan; p.kiasi=kiasi; p.muamala=b.get("muamala"); pays.save(p);
    u.plan=plan; u.locked=false; int days=plan.equals("KUBWA")?365:plan.equals("DEMO")?3:30; u.subscriptionExpiry=LocalDateTime.now().plusDays(days); users.save(u);
    return ResponseEntity.ok("Umelipa "+kiasi+" kwa Plan "+plan+". Akaunti imefunguka!");
  }
  @GetMapping("/status") public ResponseEntity<?> status(Authentication auth){
    User u=users.findByUsername(auth.getName()).orElse(null);
    if(u.subscriptionExpiry!=null && u.subscriptionExpiry.isBefore(LocalDateTime.now())) return ResponseEntity.status(403).body("Akaunti imefungwa! Lipa Plan "+u.plan+" ili kufungua.");
    return ResponseEntity.ok("Akaunti Iko Wazi - Plan: "+u.plan);
  }
}

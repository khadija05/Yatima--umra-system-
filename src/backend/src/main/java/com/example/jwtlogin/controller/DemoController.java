package com.example.jwtlogin.controller;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin(origins="*")
public class DemoController {
  @Autowired DemoRequestRepository demoRepo; @Autowired UserRepository users; @Autowired PasswordEncoder enc;
  @PostMapping("/demo-request") public ResponseEntity<?> req(@RequestBody Map<String,String> b){
    if(users.existsByUsername(b.get("email"))) return ResponseEntity.badRequest().body(Map.of("message","Email tayari inatumika"));
    User u=new User(); u.setUsername(b.get("email")); u.setPassword(enc.encode(b.get("password"))); u.setFullName(b.get("fullName")); u.setOwnerEmail(b.get("email")); u.setPlan("DEMO"); u.setPlanPrice(0.0); u.setSubscriptionExpiry(LocalDate.now().plusDays(3)); u.setDemoExpiry(LocalDate.now().plusDays(3)); u.setSubscriptionActive(true); users.save(u);
    DemoRequest d=new DemoRequest(); d.setFullName(b.get("fullName")); d.setEmail(b.get("email")); d.setPassword(b.get("password")); d.setStatus("APPROVED"); demoRepo.save(d);
    return ResponseEntity.ok(Map.of("message","DEMO imefunguliwa siku 3! Login sasa"));
  }
}

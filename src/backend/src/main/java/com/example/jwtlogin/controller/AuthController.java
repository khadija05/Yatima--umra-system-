package com.example.jwtlogin.controller;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import com.example.jwtlogin.security.jwt.JwtUtils; import com.example.jwtlogin.security.services.UserDetailsImpl; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.*; import org.springframework.security.core.Authentication; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/auth") @CrossOrigin(origins="*")
public class AuthController {
  @Autowired AuthenticationManager auth; @Autowired UserRepository users; @Autowired PasswordEncoder enc; @Autowired JwtUtils jwt;
  @PostMapping("/signin") public ResponseEntity<?> login(@RequestBody Map<String,String> b){
    try{ Authentication a=auth.authenticate(new UsernamePasswordAuthenticationToken(b.get("username"), b.get("password"))); UserDetailsImpl d=(UserDetailsImpl)a.getPrincipal(); User u=d.getUser();
      if(!u.isSubscriptionActive() || (u.getSubscriptionExpiry()!=null && u.getSubscriptionExpiry().isBefore(LocalDate.now()))){ Map<String,Object> m=new HashMap<>(); m.put("message","SUBSCRIPTION_EXPIRED"); m.put("email",u.getUsername()); m.put("plan",u.getPlan()); return ResponseEntity.status(402).body(m); }
      Map<String,Object> m=new HashMap<>(); m.put("token", jwt.generate(u.getUsername())); m.put("username",u.getUsername()); m.put("fullName",u.getFullName()); m.put("role",u.getRole()); m.put("orgName",u.getOrgName()); m.put("plan",u.getPlan()); m.put("subscriptionExpiry",u.getSubscriptionExpiry().toString()); return ResponseEntity.ok(m);
    }catch(Exception e){ return ResponseEntity.status(401).body(Map.of("message","Email au Password si sahihi")); }
  }
  @PostMapping("/signup") public ResponseEntity<?> reg(@RequestBody Map<String,String> b){
    if(users.existsByUsername(b.get("username"))) return ResponseEntity.badRequest().body(Map.of("message","Email tayari inatumika"));
    User u=new User(); u.setUsername(b.get("username")); u.setPassword(enc.encode(b.get("password"))); u.setFullName(b.get("fullName")); u.setOwnerEmail(b.get("username")); u.setOrgName(b.get("orgName")); u.setUserType(b.get("userType")); u.setPlan("KAWAIDA"); u.setSubscriptionExpiry(LocalDate.now().plusDays(30)); u.setSubscriptionActive(true); users.save(u); return ResponseEntity.ok(Map.of("message","Akaunti imefunguliwa!"));
  }
  @GetMapping("/me") public ResponseEntity<?> me(Authentication a){ UserDetailsImpl d=(UserDetailsImpl)a.getPrincipal(); User u=d.getUser(); return ResponseEntity.ok(Map.of("username",u.getUsername(),"fullName",u.getFullName()==null?"":u.getFullName(),"orgName",u.getOrgName()==null?"":u.getOrgName(),"plan",u.getPlan(),"subscriptionExpiry",u.getSubscriptionExpiry().toString())); }
}

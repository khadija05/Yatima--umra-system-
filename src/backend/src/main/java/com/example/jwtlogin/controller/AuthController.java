package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.UserRepository; import com.example.jwtlogin.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.*; import org.springframework.security.core.Authentication; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.time.LocalDate; import java.util.*;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/auth")
public class AuthController {
  @Autowired AuthenticationManager authManager; @Autowired UserRepository userRepo; @Autowired PasswordEncoder encoder; @Autowired JwtUtils jwtUtils;
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody Map<String,String> req){
    String username=req.get("username"); String email=req.get("email"); String password=req.get("password");
    if(userRepo.findByUsername(username).isPresent()) return ResponseEntity.badRequest().body("Username tayari ipo!");
    User u=new User(); u.setUsername(username); u.setEmail(email); u.setPassword(encoder.encode(password));
    u.setRole(req.getOrDefault("role","ROLE_USER")); u.setFullName(req.getOrDefault("fullName","")); u.setUserType(req.getOrDefault("userType","")); u.setOrgName(req.getOrDefault("orgName",""));
    String planCode=req.getOrDefault("plan","KAWAIDA"); Plan plan=Plan.fromCode(planCode); u.setPlan(plan.name()); u.setPlanPrice(plan.getPrice());
    if(plan==Plan.DEMO){ u.setDemoExpiry(LocalDate.now().plusDays(3)); u.setSubscriptionExpiry(LocalDate.now().plusDays(3)); u.setSubscriptionActive(true); }
    else { u.setSubscriptionExpiry(LocalDate.now().plusDays(plan.getDays())); u.setSubscriptionActive(true); }
    u.setLocked(false); u.setTotalPaid(0); userRepo.save(u);
    return ResponseEntity.ok("Umejisajili! Plan: "+plan.getDisplayName()+" - Bei: "+plan.getPrice()+" - Mwisho: "+u.getSubscriptionExpiry());
  }
  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody Map<String,String> req){
    Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(req.get("username"), req.get("password")));
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt=jwtUtils.generateJwtToken(auth);
    User u=userRepo.findByUsername(req.get("username")).get();
    if(u.isLocked()) return ResponseEntity.status(403).body("🔒 IMEFUNGWA! Lipa Plan "+u.getPlan()+" ili kuendelea.");
    if(u.getPlan().equals("DEMO") && u.getDemoExpiry()!=null && LocalDate.now().isAfter(u.getDemoExpiry()) && u.getTotalPaid()==0){ u.setLocked(true); u.setSubscriptionActive(false); userRepo.save(u); return ResponseEntity.status(403).body("DEMO imeisha! Lipa ili kuendelea."); }
    Map<String,Object> res=new HashMap<>(); res.put("accessToken",jwt); res.put("username",u.getUsername()); res.put("role",u.getRole()); res.put("plan",u.getPlan()); res.put("planPrice",u.getPlanPrice()); res.put("subscriptionExpiry",u.getSubscriptionExpiry().toString()); res.put("locked",u.isLocked()); res.put("orgName",u.getOrgName());
    return ResponseEntity.ok(res);
  }
  @GetMapping("/me") public ResponseEntity<?> me(Authentication auth){ User u=userRepo.findByUsername(auth.getName()).orElse(null); if(u==null) return ResponseEntity.notFound().build(); Map<String,Object> m=new HashMap<>(); m.put("username",u.getUsername()); m.put("plan",u.getPlan()); m.put("expiry",u.getSubscriptionExpiry().toString()); m.put("locked",u.isLocked()); m.put("totalPaid",u.getTotalPaid()); return ResponseEntity.ok(m); }
}

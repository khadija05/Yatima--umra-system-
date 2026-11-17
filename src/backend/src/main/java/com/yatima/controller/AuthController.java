package com.yatima.controller;
import com.yatima.model.User; import com.yatima.repository.UserRepository; import com.yatima.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.authentication.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/auth") @CrossOrigin("*")
public class AuthController {
  @Autowired UserRepository users; @Autowired PasswordEncoder enc; @Autowired JwtUtil jwt; @Autowired AuthenticationManager am;
  @PostMapping("/signup") public ResponseEntity<?> signup(@RequestBody Map<String,String> b){
    if(users.existsByUsername(b.get("username"))) return ResponseEntity.badRequest().body("Username tayari inatumika!");
    if(users.existsByEmail(b.get("email"))) return ResponseEntity.badRequest().body("Email tayari inatumika!");
    User u=new User(); u.username=b.get("username"); u.email=b.get("email"); u.password=enc.encode(b.get("password")); u.role=b.getOrDefault("role","ROLE_KITUO"); u.orgName=b.get("orgName"); u.plan=b.getOrDefault("plan","DEMO");
    int days=u.plan.equals("KIDOGO")?30:u.plan.equals("KAWAIDA")?30:u.plan.equals("KUBWA")?365:3;
    u.subscriptionExpiry=LocalDateTime.now().plusDays(days); users.save(u);
    return ResponseEntity.ok("Umejisajili! Plan: "+u.plan+" - Sasa ingia.");
  }
  @PostMapping("/signin") public ResponseEntity<?> signin(@RequestBody Map<String,String> b){
    User u=users.findByUsername(b.get("username")).orElse(null);
    if(u==null) return ResponseEntity.status(404).body("Mtumiaji hajapatikana!");
    if(u.locked || (u.subscriptionExpiry!=null && u.subscriptionExpiry.isBefore(LocalDateTime.now()))) return ResponseEntity.status(403).body("Akaunti imefungwa! Lipa ili kufungua. Plan: "+u.plan);
    try{ am.authenticate(new UsernamePasswordAuthenticationToken(b.get("username"),b.get("password"))); }catch(Exception e){ return ResponseEntity.status(401).body("Password sio sahihi!"); }
    String token=jwt.generate(u.username);
    Map<String,Object> res=new HashMap<>(); res.put("accessToken",token); res.put("username",u.username); res.put("email",u.email); res.put("role",u.role); res.put("plan",u.plan); res.put("subscriptionExpiry",u.subscriptionExpiry.toString()); res.put("id",u.id);
    return ResponseEntity.ok(res);
  }
}

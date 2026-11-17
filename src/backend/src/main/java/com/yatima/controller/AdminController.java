package com.yatima.controller;
import com.yatima.model.User;
import com.yatima.repository.PaymentRepository;
import com.yatima.repository.UserRepository;
import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/admin") @CrossOrigin("*")
public class AdminController {
  private final UserRepository userRepo; private final PaymentRepository payRepo; private final JwtUtil jwt;
  public AdminController(UserRepository u, PaymentRepository p, JwtUtil j){userRepo=u;payRepo=p;jwt=j;}
  private String role(String a){try{return (String)jwt.parse(a).get("role");}catch(Exception e){return "";}} private String user(String a){try{return jwt.parse(a).getSubject();}catch(Exception e){return "";}} private boolean isSuper(String a){return "SUPER_ADMIN".equals(role(a));}
  @GetMapping("/users") public ResponseEntity<?> users(@RequestHeader(value="Authorization",required=false) String auth){if(isSuper(auth)){List<User> l=userRepo.findAll(); l.forEach(u->u.setPassword(null)); return ResponseEntity.ok(l);} if("TENANT_ADMIN".equals(role(auth))){List<User> l=userRepo.findByTenantId(user(auth)); l.forEach(u->u.setPassword(null)); return ResponseEntity.ok(l);} return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));}
  @GetMapping("/stats") public ResponseEntity<?> stats(@RequestHeader(value="Authorization",required=false) String auth){if(isSuper(auth)){long total=userRepo.count(), active=userRepo.findAll().stream().filter(u->"active".equals(u.getStatus())).count(), expired=userRepo.findAll().stream().filter(u->"expired".equals(u.getStatus())).count(); double money=payRepo.findAll().stream().mapToDouble(p->p.getAmount()==null?0:p.getAmount()).sum(); return ResponseEntity.ok(Map.of("total",total,"active",active,"expired",expired,"money",money));} if("TENANT_ADMIN".equals(role(auth))){List<User> mine=userRepo.findByTenantId(user(auth)); return ResponseEntity.ok(Map.of("total",mine.size(),"active",mine.stream().filter(u->"active".equals(u.getStatus())).count(),"expired",mine.stream().filter(u->"expired".equals(u.getStatus())).count(),"money",0));} return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa"));}
  @PostMapping("/approve/{username}") public ResponseEntity<?> approve(@PathVariable String username,@RequestHeader(value="Authorization",required=false) String auth){if(!isSuper(auth)&&!"TENANT_ADMIN".equals(role(auth))) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa")); Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build(); User u=o.get(); u.setStatus("active"); u.setApprovedByKhadija(true); if(u.getExpiresAt()==null||u.getExpiresAt().isBefore(LocalDateTime.now())) u.setExpiresAt(LocalDateTime.now().plusDays(30)); userRepo.save(u); return ResponseEntity.ok(Map.of("message","Amethibitishwa na Khadija"));}
  @PostMapping("/lock/{username}") public ResponseEntity<?> lock(@PathVariable String username,@RequestHeader(value="Authorization",required=false) String auth){if(!isSuper(auth)&&!"TENANT_ADMIN".equals(role(auth))) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa")); Optional<User> o=userRepo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build(); User u=o.get(); u.setStatus("expired"); userRepo.save(u); return ResponseEntity.ok(Map.of("message","Amefungwa"));}
}

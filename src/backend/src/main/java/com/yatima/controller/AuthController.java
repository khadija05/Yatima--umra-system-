package com.yatima.controller;
import com.yatima.model.User; import com.yatima.repository.*; import com.yatima.service.SubscriptionService; import com.yatima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.ResponseEntity; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/auth") @CrossOrigin("*")
public class AuthController {
  private final UserRepository repo; private final PasswordEncoder enc; private final JwtUtil jwt;
  @Value("${pbz.account}") private String pbzAcc; @Value("${pbz.name}") private String pbzName;
  public AuthController(UserRepository r, PasswordEncoder e, JwtUtil j){repo=r;enc=e;jwt=j;}
  private Map<String,Object> toUser(User u){ Map<String,Object> m=new HashMap<>(); m.put("username",u.getUsername()); m.put("email",u.getEmail()); m.put("phone",u.getPhone()); m.put("role",u.getRole()); m.put("tenantId",u.getTenantId()); m.put("org",u.getOrg()); m.put("plan",u.getPlan()); m.put("status",u.getStatus()); m.put("expiresAt",u.getExpiresAt()); m.put("requirements",u.getRequirements()); m.put("myPbzAccount",u.getMyPbzAccount()); m.put("myPbzName",u.getMyPbzName()); m.put("approvedByKhadija",u.getApprovedByKhadija()); return m; }
  @PostMapping("/register") public ResponseEntity<?> register(@RequestBody Map<String,String> b){
    String username=b.getOrDefault("username","").trim().toLowerCase();
    if(repo.findByUsername(username).isPresent()) return ResponseEntity.badRequest().body(Map.of("message","Username imeshachukuliwa"));
    if(username.length()<3) return ResponseEntity.badRequest().body(Map.of("message","Username 3+"));
    User u=new User(); u.setUsername(username); u.setEmail(b.getOrDefault("email","")); u.setPhone(b.getOrDefault("phone","")); u.setPassword(enc.encode(b.getOrDefault("password","123456")));
    String role=b.getOrDefault("role","TENANT_ADMIN"); u.setRole(role);
    u.setOrg(b.getOrDefault("org","")); u.setPlan(b.getOrDefault("plan","FREE-DEMO"));
    u.setRequirements(b.getOrDefault("requirements","")); u.setMyPbzAccount(b.getOrDefault("myPbzAccount","")); u.setMyPbzName(b.getOrDefault("myPbzName",""));
    u.setOrgPhone(b.getOrDefault("orgPhone","")); u.setOrgLocation(b.getOrDefault("orgLocation",""));
    // TENANT_ADMIN tenant yake ni username yake mwenyewe. Mthamini tenant ni ya admin aliyemchagua
    if("TENANT_ADMIN".equals(role)||"OWNER".equals(role)||"Admin".equals(role)) u.setTenantId(username);
    else u.setTenantId(b.getOrDefault("tenantId",""));
    Map<String,Object> info=SubscriptionService.planInfo(u.getPlan());
    u.setAmount((Double)info.get("price")); u.setStatus("pending"); u.setApprovedByKhadija(false);
    if("FREE-DEMO".equals(u.getPlan())){ u.setStatus("active"); u.setApprovedByKhadija(true); u.setExpiresAt(LocalDateTime.now().plusDays(3)); }
    else u.setExpiresAt(LocalDateTime.now());
    u.setPbzAccount(pbzAcc); u.setPbzName(pbzName); u.setDate(LocalDateTime.now().toString());
    repo.save(u);
    String token=jwt.generate(u.getUsername(), u.getRole(), u.getTenantId());
    return ResponseEntity.ok(Map.of("token",token,"user",toUser(u),"message","Umejisajili! Lipa ili uthibitishwe na Khadija."));
  }
  @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String> b){
    String username=b.getOrDefault("username","").trim().toLowerCase();
    var o=repo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.status(404).body(Map.of("message","Username haipo"));
    User u=o.get(); if(!enc.matches(b.getOrDefault("password",""), u.getPassword())) return ResponseEntity.status(403).body(Map.of("message","Password sio sahihi"));
    // Auto-expire check
    if(!"SUPER_ADMIN".equals(u.getRole()) && u.getExpiresAt()!=null && u.getExpiresAt().isBefore(LocalDateTime.now()) && !"FREE-DEMO".equals(u.getPlan())){ u.setStatus("expired"); repo.save(u); }
    String token=jwt.generate(u.getUsername(), u.getRole(), u.getTenantId());
    return ResponseEntity.ok(Map.of("token",token,"user",toUser(u)));
  }
  @GetMapping("/pbz") public ResponseEntity<?> pbz(@RequestHeader(value="Authorization",required=false) String auth){
    try{ jwt.parse(auth); }catch(Exception e){ return ResponseEntity.status(403).body(Map.of("message","Ingia kwanza")); }
    return ResponseEntity.ok(Map.of("pbzAccount",pbzAcc,"pbzName",pbzName));
  }
  @PutMapping("/me/requirements") public ResponseEntity<?> req(@RequestBody Map<String,String> b, @RequestHeader(value="Authorization",required=false) String auth){
    try{ String username=jwt.parse(auth).getSubject(); var o=repo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.notFound().build(); User u=o.get(); u.setRequirements(b.getOrDefault("requirements","")); repo.save(u); return ResponseEntity.ok(Map.of("message","Requirement zimehifadhiwa")); }catch(Exception e){ return ResponseEntity.status(403).body(Map.of("message","Ingia kwanza")); }
  }
  @PostMapping("/forgot-password") public ResponseEntity<?> forgot(@RequestBody Map<String,String> b){
    String username=b.getOrDefault("username","").trim().toLowerCase();
    var o=repo.findByUsername(username); if(o.isEmpty()) return ResponseEntity.ok(Map.of("message","Kama akaunti ipo, ombi limetumwa. Wasiliana na Khadija kupitia WhatsApp."));
    User u=o.get(); String token=UUID.randomUUID().toString().substring(0,8).toUpperCase(); u.setResetToken(token); u.setResetTokenExpiry(LocalDateTime.now().plusHours(2)); repo.save(u);
    return ResponseEntity.ok(Map.of("message","Ombi limetumwa! Mpe Khadija WhatsApp code hii: "+token+" - atakupatia password mpya."));
  }
  @PostMapping("/reset-password") public ResponseEntity<?> reset(@RequestBody Map<String,String> b){
    var o=repo.findByResetToken(b.getOrDefault("token","")); if(o.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Code sio sahihi"));
    User u=o.get(); if(u.getResetTokenExpiry()==null||u.getResetTokenExpiry().isBefore(LocalDateTime.now())) return ResponseEntity.badRequest().body(Map.of("message","Code imeisha muda"));
    u.setPassword(enc.encode(b.getOrDefault("newPassword","123456"))); u.setResetToken(null); u.setResetTokenExpiry(null); u.setLocked(false); repo.save(u);
    return ResponseEntity.ok(Map.of("message","Password imebadilishwa! Sasa ingia."));
  }
}

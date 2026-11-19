package com.yatima.controller;
import com.yatima.model.User;
import com.yatima.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
  private final UserRepository users;
  public AuthController(UserRepository u){ this.users = u; }
  private String env(String k, String def){ String v = System.getenv(k); return (v != null && !v.isBlank()) ? v : def; }

  @PostMapping("/login")
  public Map<String,Object> login(@RequestBody Map<String,String> b){
    String ph = b.get("phone"); String pw = b.get("password"); String un = b.get("username");
    String loginKey = (ph != null && !ph.isBlank()) ? ph : un;
    String ownerPhone = env("OWNER_PHONE", ""); String ownerPass = env("OWNER_PASSWORD", ""); String ownerUser = env("OWNER_USERNAME", "owner");
    if(!ownerPhone.isEmpty() && !ownerPass.isEmpty() && (ownerPhone.equals(loginKey) || ownerUser.equals(loginKey)) && ownerPass.equals(pw)){
      return Map.of("success",true,"name","Owner","username",ownerUser,"phone",ownerPhone,"role","OWNER","plan","SPECIAL","status","active","approvedByKhadija",true);
    }
    String adminPhone = env("ADMIN_PHONE", ""); String adminPass = env("ADMIN_PASSWORD", ""); String adminUser = env("ADMIN_USERNAME", "admin");
    if(!adminPhone.isEmpty() && !adminPass.isEmpty() && (adminPhone.equals(loginKey) || adminUser.equals(loginKey)) && adminPass.equals(pw)){
      return Map.of("success",true,"name","Khadija","username",adminUser,"phone",adminPhone,"role","ADMIN","plan","SPECIAL","status","active","approvedByKhadija",true);
    }
    Optional<User> opt = (ph != null ? users.findByPhone(ph) : Optional.<User>empty()).or(() -> un != null ? users.findByUsername(un) : Optional.empty());
    if(opt.isEmpty()) return Map.of("error","NOT_FOUND","message","Akaunti haipo. Jisajili kwanza.");
    User user = opt.get();
    if(!user.getPassword().equals(pw)) return Map.of("error","WRONG_PASSWORD","message","Password si sahihi.");
    if(Boolean.TRUE.equals(user.getLocked())) return Map.of("error","LOCKED","message","🔒 Akaunti imefungwa. Lipa 50,000 TZS ili ufunguliwe.");
    if(!Boolean.TRUE.equals(user.getApprovedByKhadija())) return Map.of("error","NOT_APPROVED","message","⏳ Bado haijathibitishwa na Khadija. Subiri kidogo.");
    if(user.getExpiresAt()!=null && LocalDateTime.now().isAfter(user.getExpiresAt())){
      user.setStatus("expired"); user.setLocked(true); users.save(user);
      return Map.of("error","EXPIRED","message","⏰ Muda wako umeisha! Lipa 50,000 TZS kuendelea.");
    }
    if(!"active".equals(user.getStatus())) return Map.of("error","NOT_ACTIVE","message","Akaunti si active. Lipa kwanza.");
    return Map.of("success",true,"id",user.getId(),"username",user.getUsername(),"name",user.getName(),"phone",user.getPhone(),"role",user.getRole(),"plan",user.getPlan(),"status",user.getStatus(),"approvedByKhadija",true,"expiresAt",user.getExpiresAt()==null?"":user.getExpiresAt().toString());
  }

  @PostMapping("/register")
  public Map<String,Object> register(@RequestBody User u){
    if(u.getPhone()!=null && !u.getPhone().isEmpty() && users.findByPhone(u.getPhone()).isPresent()) return Map.of("error","ALREADY_REGISTERED","message","Namba hii imeshajisajiliwa. INGIA tu.");
    if(u.getName()==null || u.getName().isBlank()) u.setName(u.getUsername());
    if(u.getRole()==null) u.setRole("Mthamini");
    if(u.getPlan()==null) u.setPlan("FREE-DEMO");
    if("FREE-DEMO".equals(u.getPlan())) u.setExpiresAt(LocalDateTime.now().plusDays(3));
    if("MONTHLY".equals(u.getPlan())) u.setExpiresAt(LocalDateTime.now().plusDays(30));
    u.setStatus("pending"); u.setApprovedByKhadija(false);
    User s = users.save(u);
    return Map.of("success",true,"id",s.getId(),"username",s.getUsername(),"message","Umejisajili! Subiri Khadija akuthibitishe.");
  }

  @PostMapping("/pay")
  public Map<String,Object> pay(@RequestBody Map<String,String> b){
    String ph=b.get("phone"); String plan=b.getOrDefault("plan","MONTHLY");
    Optional<User> opt=users.findByPhone(ph);
    if(opt.isEmpty()) return Map.of("error","NOT_FOUND","message","Akaunti haipo. Jisajili kwanza.");
    User u=opt.get(); u.setPlan(plan); u.setStatus("waiting-payment"); users.save(u);
    return Map.of("success",true,"message","Malipo yamepokelewa! Subiri Khadija athibitishe.");
  }

  @PutMapping("/approve/{id}") public User approve(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setApprovedByKhadija(true); u.setStatus("active"); u.setLocked(false); if("FREE-DEMO".equals(u.getPlan())) u.setExpiresAt(LocalDateTime.now().plusDays(3)); else u.setExpiresAt(LocalDateTime.now().plusDays(30)); return users.save(u); }
  @PutMapping("/lock/{id}") public User lock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(true); u.setStatus("locked"); return users.save(u); }
  @PutMapping("/unlock/{id}") public User unlock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(false); u.setStatus("active"); u.setApprovedByKhadija(true); u.setExpiresAt(LocalDateTime.now().plusDays(30)); return users.save(u); }
  @GetMapping("/all") public List<User> all(){ return users.findAll(); }
}

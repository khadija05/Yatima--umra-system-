package com.yatima.controller;
import com.yatima.model.User;
import com.yatima.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*; import java.time.LocalDateTime;
@RestController @RequestMapping("/api/auth") @CrossOrigin("*")
public class AuthController {
  private final UserRepository users; public AuthController(UserRepository u){ this.users=u; }
  private String env(String k,String d){ String v=System.getenv(k); return (v!=null&&!v.isBlank())?v:d; }
  private int daysForPlan(String p){
    if(p==null) return 3;
    if(p.equals("JARIBIO") || p.equals("FREE-DEMO")) return 3;
    if(p.equals("KIDOGO") || p.equals("KUBWA") || p.equals("KHADIJA_SPECIAL") || p.equals("MONTHLY")) return 30;
    return 30;
  }
  @PostMapping("/login") public Map<String,Object> login(@RequestBody Map<String,String> b){
    String ph=b.get("phone"), pw=b.get("password"), un=b.get("username"); String key=(ph!=null&&!ph.isBlank())?ph:un;
    String oPh=env("OWNER_PHONE",""), oPw=env("OWNER_PASSWORD",""), oU=env("OWNER_USERNAME","owner");
    if(!oPh.isEmpty()&&!oPw.isEmpty()&&(oPh.equals(key)||oU.equals(key))&&oPw.equals(pw)) return Map.of("success",true,"name","Owner","username",oU,"phone",oPh,"role","OWNER","plan","SPECIAL","status","active","approvedByKhadija",true);
    String aPh=env("ADMIN_PHONE",""), aPw=env("ADMIN_PASSWORD",""), aU=env("ADMIN_USERNAME","admin");
    if(!aPh.isEmpty()&&!aPw.isEmpty()&&(aPh.equals(key)||aU.equals(key))&&aPw.equals(pw)) return Map.of("success",true,"name","Khadija","username",aU,"phone",aPh,"role","ADMIN","plan","SPECIAL","status","active","approvedByKhadija",true);
    if("khadija".equals(key) && "khadija123".equals(pw)) return Map.of("success",true,"name","Khadija","username","khadija","phone","0887387001","role","ADMIN","plan","SPECIAL","status","active","approvedByKhadija",true);
    Optional<User> opt=(ph!=null?users.findByPhone(ph):Optional.<User>empty()).or(()->un!=null?users.findByUsername(un):Optional.empty());
    if(opt.isEmpty()) return Map.of("error","NOT_FOUND","message","Akaunti haipo. Jisajili kwanza.");
    User user=opt.get(); if(!user.getPassword().equals(pw)) return Map.of("error","WRONG_PASSWORD","message","Password si sahihi.");
    if(Boolean.TRUE.equals(user.getLocked())) return Map.of("error","LOCKED","message","🔒 Akaunti imefungwa. Lipa ili ufunguliwe.");
    if(!Boolean.TRUE.equals(user.getApprovedByKhadija())) return Map.of("error","NOT_APPROVED","message","⏳ Bado haijathibitishwa na Mmiliki. Subiri kidogo.");
    if(user.getExpiresAt()!=null && LocalDateTime.now().isAfter(user.getExpiresAt())){ user.setStatus("expired"); user.setLocked(true); users.save(user); return Map.of("error","EXPIRED","message","⏰ Muda wako umeisha! Lipa ili uendelee."); }
    if(!"active".equals(user.getStatus())) return Map.of("error","NOT_ACTIVE","message","Akaunti si active. Lipa kwanza.");
    return Map.of("success",true,"id",user.getId(),"username",user.getUsername(),"name",user.getName(),"phone",user.getPhone(),"role",user.getRole(),"plan",user.getPlan(),"status",user.getStatus(),"approvedByKhadija",true,"expiresAt",user.getExpiresAt()==null?"":user.getExpiresAt().toString());
  }
  @PostMapping("/register") public Map<String,Object> register(@RequestBody Map<String,String> m){
    String phone=m.get("phone"); String username=m.get("username");
    if(phone!=null&&!phone.isEmpty()&&users.findByPhone(phone).isPresent()) return Map.of("error","ALREADY_REGISTERED","message","Namba hii imeshajisajiliwa. INGIA tu.");
    if(username!=null&&users.findByUsername(username).isPresent()) return Map.of("error","ALREADY_REGISTERED","message","Username hii imeshachukuliwa.");
    User u=new User();
    String full=m.get("fullName"); if(full==null) full=m.get("name"); if(full==null||full.isBlank()) full=username;
    u.setName(full); u.setUsername(username); u.setPhone(phone); u.setPassword(m.get("password"));
    String role=m.get("role"); if(role==null) role="Mthamini"; u.setRole(role);
    String plan=m.get("plan"); if(plan==null) plan="JARIBIO"; u.setPlan(plan);
    u.setExpiresAt(LocalDateTime.now().plusDays(daysForPlan(plan)));
    u.setStatus("pending"); u.setApprovedByKhadija(false);
    String txn=m.get("paymentTxn"); if(txn!=null&&!txn.isBlank()) u.setStatus("waiting-payment");
    User s=users.save(u); return Map.of("success",true,"id",s.getId(),"username",s.getUsername());
  }
  @PostMapping("/pay") public Map<String,Object> pay(@RequestBody Map<String,String> b){ Optional<User> opt=users.findByPhone(b.get("phone")); if(opt.isEmpty()) return Map.of("error","NOT_FOUND","message","Akaunti haipo. Jisajili kwanza."); User u=opt.get(); u.setPlan(b.getOrDefault("plan","KIDOGO")); u.setStatus("waiting-payment"); users.save(u); return Map.of("success",true); }
  @PutMapping("/approve/{id}") public User approve(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setApprovedByKhadija(true); u.setStatus("active"); u.setLocked(false); u.setExpiresAt(LocalDateTime.now().plusDays(daysForPlan(u.getPlan()))); return users.save(u); }
  @PutMapping("/lock/{id}") public User lock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(true); u.setStatus("locked"); return users.save(u); }
  @GetMapping("/all") public List<User> all(){ return users.findAll(); }
}

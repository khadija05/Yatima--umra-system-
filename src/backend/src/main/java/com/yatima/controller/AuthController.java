// AuthController.java - User asiweze kujisajili tena
package com.yatima.controller; import com.yatima.model.User; import com.yatima.repository.UserRepository; import org.springframework.web.bind.annotation.*; import java.util.*; import java.time.LocalDateTime;
@RestController @RequestMapping("/api/auth") @CrossOrigin("*") public class AuthController {
  private final UserRepository users; public AuthController(UserRepository u){this.users=u;}
  @PostMapping("/register") public Map<String,Object> register(@RequestBody User u){
    if(u.getPhone()!=null && !u.getPhone().isEmpty() && users.findByPhone(u.getPhone()).isPresent()){ User old=users.findByPhone(u.getPhone()).get(); return Map.of("error","ALREADY_REGISTERED","message","Namba "+u.getPhone()+" imesajisajiliwa tayari kama "+old.getUsername()+". Tafadhali INGIA tu, huwezi kujisajili tena.","username",old.getUsername(),"role",old.getRole()); }
    if(u.getUsername()!=null && !u.getUsername().isEmpty() && users.findByUsername(u.getUsername()).isPresent()) return Map.of("error","ALREADY_REGISTERED","message","Username "+u.getUsername()+" imeshatumika. Tafadhali INGIA.");
    if(u.getEmail()!=null && !u.getEmail().isEmpty() && users.findByEmail(u.getEmail()).isPresent()) return Map.of("error","ALREADY_REGISTERED","message","Email "+u.getEmail()+" imesajisajiliwa tayari. Tafadhali INGIA tu.");
    if(u.getName()==null) u.setName(u.getUsername()); if(u.getUsername()==null) u.setUsername(u.getName());
    if(u.getRole()==null) u.setRole("Mthamini"); if(u.getPlan()==null) u.setPlan("FREE-DEMO");
    if("FREE-DEMO".equals(u.getPlan())) u.setExpiresAt(LocalDateTime.now().plusDays(3));
    u.setStatus("pending"); u.setApprovedByKhadija(false);
    User s=users.save(u); return Map.of("success",true,"id",s.getId(),"username",s.getUsername(),"name",s.getName(),"phone",s.getPhone(),"role",s.getRole(),"plan",s.getPlan(),"status",s.getStatus(),"message","Umejisajili! Sasa INGIA, usijisajili tena.");
  }
  @PostMapping("/login") public Map<String,Object> login(@RequestBody Map<String,String> b){
    String ph=b.get("phone"), pw=b.get("password"), un=b.get("username");
    if("0778709707".equals(ph)&&"owner123".equals(pw)) return Map.of("name","KHADIJA DENGE","username","owner","phone",ph,"role","OWNER","plan","SPECIAL","status","active","approvedByKhadija",true,"ownerName","KHADIJA DENGE","ownerPhone","0778709707");
    if("0778709707".equals(ph)&&"admin123".equals(pw)) return Map.of("name","Admin Khadija","username","khadija","phone",ph,"role","ADMIN","plan","SPECIAL","status","active","approvedByKhadija",true);
    var user=(ph!=null? users.findByPhone(ph):Optional.<User>empty()).or(()->un!=null? users.findByUsername(un):Optional.empty()).orElseThrow(()->new RuntimeException("User not found"));
    if(!user.getPassword().equals(pw)) throw new RuntimeException("Password wrong");
    if(Boolean.TRUE.equals(user.getLocked())) throw new RuntimeException("Akaunti imefungwa. Wasiliana na Khadija 0778709707");
    if(user.getExpiresAt()!=null && LocalDateTime.now().isAfter(user.getExpiresAt()) && !"active".equals(user.getStatus())) return Map.of("error","EXPIRED","message","Jaribio la siku 3 limeisha. Lipa ili kuendelea. PBZ 0887387001 KHADIJA DENGE");
    return Map.of("id",user.getId(),"username",user.getUsername(),"name",user.getName(),"phone",user.getPhone(),"role",user.getRole(),"plan",user.getPlan(),"status",user.getStatus(),"org",user.getOrg(),"tenantId",user.getTenantId(),"approvedByKhadija",user.getApprovedByKhadija());
  }
  @GetMapping("/check/{phone}") public Map<String,Object> check(@PathVariable String phone){ var u=users.findByPhone(phone); if(u.isPresent()) return Map.of("exists",true,"message","Umesajisajili tayari kama "+u.get().getUsername()+". Tafadhali INGIA tu.","username",u.get().getUsername()); return Map.of("exists",false,"message","Karibu, unaweza kujisajili."); }
  @PutMapping("/approve/{id}") public User approve(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setApprovedByKhadija(true); u.setStatus("active"); return users.save(u); }
  @PutMapping("/lock/{id}") public User lock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(true); u.setStatus("locked"); return users.save(u); }
  @PutMapping("/unlock/{id}") public User unlock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(false); u.setStatus("active"); return users.save(u); }
  @GetMapping("/all") public List<User> all(){ return users.findAll(); }
}

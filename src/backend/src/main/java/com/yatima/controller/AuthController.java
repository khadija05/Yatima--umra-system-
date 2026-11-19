// AuthController.java - SAFI, hakuna password iliyoandikwa
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

  // Password za Owner / Admin zinatoka kwenye Render Environment, SIO kwenye code
  private String env(String k, String def){
    String v = System.getenv(k);
    return (v != null && !v.isBlank()) ? v : def;
  }

  @PostMapping("/register")
  public Map<String,Object> register(@RequestBody User u){
    if(u.getPhone()!=null && !u.getPhone().isEmpty() && users.findByPhone(u.getPhone()).isPresent()){
      return Map.of("error","ALREADY_REGISTERED","message","Namba hii imeshajisajiliwa tayari. Tafadhali INGIA tu, huwezi kujisajili tena.");
    }
    if(u.getUsername()!=null && !u.getUsername().isEmpty() && users.findByUsername(u.getUsername()).isPresent()){
      return Map.of("error","ALREADY_REGISTERED","message","Username hii imeshatumika. Tafadhali INGIA.");
    }
    if(u.getEmail()!=null && !u.getEmail().isEmpty() && users.findByEmail(u.getEmail()).isPresent()){
      return Map.of("error","ALREADY_REGISTERED","message","Email hii imeshajisajiliwa tayari. Tafadhali INGIA tu.");
    }
    if(u.getName()==null || u.getName().isBlank()) u.setName(u.getUsername());
    if(u.getUsername()==null || u.getUsername().isBlank()) u.setUsername(u.getName());
    if(u.getRole()==null) u.setRole("Mthamini");
    if(u.getPlan()==null) u.setPlan("FREE-DEMO");
    if("FREE-DEMO".equals(u.getPlan())) u.setExpiresAt(LocalDateTime.now().plusDays(3));
    u.setStatus("pending");
    u.setApprovedByKhadija(false);
    User s = users.save(u);
    return Map.of("success",true,"id",s.getId(),"username",s.getUsername(),"name",s.getName(),"phone",s.getPhone(),"role",s.getRole(),"plan",s.getPlan(),"status",s.getStatus(),"message","Umejisajili! Sasa INGIA, usijisajili tena.");
  }

  @PostMapping("/login")
  public Map<String,Object> login(@RequestBody Map<String,String> b){
    String ph = b.get("phone");
    String pw = b.get("password");
    String un = b.get("username");
    String loginKey = (ph != null && !ph.isBlank()) ? ph : un;

    // 1. OWNER - kutoka Env tu, hakuna password kwenye code
    String ownerPhone = env("OWNER_PHONE", "");
    String ownerPass = env("OWNER_PASSWORD", "");
    String ownerUser = env("OWNER_USERNAME", "owner");
    if(!ownerPhone.isEmpty() && !ownerPass.isEmpty()
        && (ownerPhone.equals(loginKey) || ownerUser.equals(loginKey))
        && ownerPass.equals(pw)){
      return Map.of("name","Owner","username",ownerUser,"phone",ownerPhone,"role","OWNER","plan","SPECIAL","status","active","approvedByKhadija",true);
    }

    // 2. ADMIN - kutoka Env tu, hakuna password kwenye code
    String adminPhone = env("ADMIN_PHONE", "");
    String adminPass = env("ADMIN_PASSWORD", "");
    String adminUser = env("ADMIN_USERNAME", "admin");
    if(!adminPhone.isEmpty() && !adminPass.isEmpty()
        && (adminPhone.equals(loginKey) || adminUser.equals(loginKey))
        && adminPass.equals(pw)){
      return Map.of("name","Admin","username",adminUser,"phone",adminPhone,"role","ADMIN","plan","SPECIAL","status","active","approvedByKhadija",true);
    }

    // 3. Watumiaji wa kawaida - kutoka Database
    Optional<User> opt = (ph != null ? users.findByPhone(ph) : Optional.<User>empty())
        .or(() -> un != null ? users.findByUsername(un) : Optional.empty());
    User user = opt.orElseThrow(() -> new RuntimeException("User not found"));
    if(!user.getPassword().equals(pw)) throw new RuntimeException("Password wrong");
    if(Boolean.TRUE.equals(user.getLocked())) throw new RuntimeException("Akaunti imefungwa. Wasiliana na Owner.");
    if(user.getExpiresAt() != null && LocalDateTime.now().isAfter(user.getExpiresAt()) && !"active".equals(user.getStatus())){
      return Map.of("error","EXPIRED","message","Jaribio la siku 3 limeisha. Lipa ili kuendelea.");
    }
    return Map.of("id",user.getId(),"username",user.getUsername(),"name",user.getName(),"phone",user.getPhone(),"role",user.getRole(),"plan",user.getPlan(),"status",user.getStatus(),"org",user.getOrg() == null ? "" : user.getOrg(),"approvedByKhadija",user.getApprovedByKhadija() == null ? false : user.getApprovedByKhadija());
  }

  @GetMapping("/check/{phone}")
  public Map<String,Object> check(@PathVariable String phone){
    var u = users.findByPhone(phone);
    if(u.isPresent()) return Map.of("exists",true,"message","Umesajisajili tayari. Tafadhali INGIA tu.");
    return Map.of("exists",false,"message","Karibu, unaweza kujisajili.");
  }

  @PutMapping("/approve/{id}") public User approve(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setApprovedByKhadija(true); u.setStatus("active"); return users.save(u); }
  @PutMapping("/lock/{id}") public User lock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(true); u.setStatus("locked"); return users.save(u); }
  @PutMapping("/unlock/{id}") public User unlock(@PathVariable Long id){ User u=users.findById(id).orElseThrow(); u.setLocked(false); u.setStatus("active"); return users.save(u); }
  @GetMapping("/all") public List<User> all(){ return users.findAll(); }
}

package com.yatima.controller;
import com.yatima.model.Payment;
import com.yatima.model.User;
import com.yatima.repository.PaymentRepository;
import com.yatima.repository.UserRepository;
import com.yatima.service.SubscriptionService;
import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/auth") @CrossOrigin("*")
public class AuthController {
  private final UserRepository userRepo; private final PaymentRepository payRepo; private final PasswordEncoder encoder; private final JwtUtil jwt;
  public AuthController(UserRepository u, PaymentRepository p, PasswordEncoder e, JwtUtil j){userRepo=u;payRepo=p;encoder=e;jwt=j;}
  @PostMapping("/register") public ResponseEntity<?> register(@RequestBody Map<String,String> body){
    String username=body.get("username"), email=body.get("email"), phone=body.get("phone"), password=body.get("password");
    String org=body.getOrDefault("org",""), role=body.getOrDefault("role","Mthamini"), plan=body.getOrDefault("plan","FREE-0");
    String muamala=body.getOrDefault("muamala",""), requirements=body.getOrDefault("requirements",""), myPbz=body.getOrDefault("myPbzAccount",""), myPbzName=body.getOrDefault("myPbzName",""), tenantIn=body.getOrDefault("tenantId","");
    if(username==null||username.length()<3) return ResponseEntity.badRequest().body(Map.of("message","Username 3+"));
    if(email==null||!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) return ResponseEntity.badRequest().body(Map.of("message","Email sio sahihi"));
    if(phone!=null&&!phone.isEmpty()&&!phone.matches("^0[67]\\d{8}$")) return ResponseEntity.badRequest().body(Map.of("message","Namba sio sahihi 07XX/06XX"));
    if(password==null||password.length()<6) return ResponseEntity.badRequest().body(Map.of("message","Password 6+"));
    if(userRepo.findByUsername(username).isPresent()) return ResponseEntity.badRequest().body(Map.of("message","Username tayari inatumika"));
    if(userRepo.findByEmail(email).isPresent()) return ResponseEntity.badRequest().body(Map.of("message","Email tayari inatumika"));
    boolean isFree=plan.startsWith("FREE"); if(!isFree&&(muamala==null||muamala.length()<4)) return ResponseEntity.badRequest().body(Map.of("message","Weka Muamala wa PBZ kwa KHADIJA DENGE"));
    User u=new User(); u.setUsername(username); u.setEmail(email); u.setPhone(phone); u.setPassword(encoder.encode(password)); u.setOrg(org); u.setPlan(plan); u.setAmount(SubscriptionService.amountForPlan(plan)); u.setMuamala(muamala); u.setPbzAccount("0887387001"); u.setPbzName("KHADIJA DENGE"); u.setMyPbzAccount(myPbz); u.setMyPbzName(myPbzName); u.setRequirements(requirements); u.setPaymentMethod(isFree?"FREE":"PBZ"); u.setStatus(isFree?"active":"pending"); u.setExpiresAt(LocalDateTime.now().plusDays(SubscriptionService.daysForPlan(plan)));
    if("TENANT_ADMIN".equals(role)||"OWNER".equals(role)||"Admin".equals(role)){u.setRole("TENANT_ADMIN");u.setTenantId(username);}
    else if("SUPER_ADMIN".equals(role)){u.setRole("SUPER_ADMIN");u.setTenantId("KHADIJA");}
    else{u.setRole(role);u.setTenantId(tenantIn.isEmpty()?username:tenantIn);}
    userRepo.save(u);
    Payment p=new Payment(); p.setUsername(username); p.setTenantId(u.getTenantId()); p.setPlan(plan); p.setAmount(u.getAmount()); p.setMuamala(muamala); p.setMethod(u.getPaymentMethod()); p.setPbzAccount("0887387001"); p.setPbzName("KHADIJA DENGE"); p.setStatus(u.getStatus()); payRepo.save(p);
    if(phone!=null) SubscriptionService.sendWhatsApp(phone,"Karibu "+username+"! Umejisajili kama "+u.getRole()+" Plan "+plan+" PBZ 0887387001 - KHADIJA DENGE");
    String token=jwt.generate(username,u.getRole());
    Map<String,Object> m=new HashMap<>(); m.put("username",username); m.put("role",u.getRole()); m.put("tenantId",u.getTenantId()); m.put("org",org); m.put("plan",plan); m.put("requirements",requirements); m.put("myPbzAccount",myPbz); m.put("myPbzName",myPbzName); m.put("expiresAt",u.getExpiresAt().toString()); m.put("status",u.getStatus()); m.put("phone",phone);
    return ResponseEntity.ok(Map.of("message","Umejisajili!","token",token,"user",m));
  }
  @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String> body){
    String username=body.get("username"), password=body.get("password"); Optional<User> opt=userRepo.findByUsername(username); if(opt.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Username haipo"));
    User u=opt.get(); if(!encoder.matches(password,u.getPassword())) return ResponseEntity.badRequest().body(Map.of("message","Password sio sahihi"));
    if("active".equals(u.getStatus())&&u.getExpiresAt()!=null&&u.getExpiresAt().isBefore(LocalDateTime.now())){u.setStatus("expired");userRepo.save(u);SubscriptionService.sendWhatsApp(u.getPhone(),"Plan yako imeisha. Lipa PBZ 0887387001 - KHADIJA DENGE");}
    String token=jwt.generate(username,u.getRole());
    Map<String,Object> m=new HashMap<>(); m.put("username",u.getUsername()); m.put("role",u.getRole()); m.put("tenantId",u.getTenantId()); m.put("org",u.getOrg()); m.put("plan",u.getPlan()); m.put("requirements",u.getRequirements()); m.put("myPbzAccount",u.getMyPbzAccount()); m.put("myPbzName",u.getMyPbzName()); m.put("expiresAt",u.getExpiresAt()!=null?u.getExpiresAt().toString():""); m.put("status",u.getStatus()); m.put("phone",u.getPhone());
    return ResponseEntity.ok(Map.of("token",token,"user",m));
  }
}

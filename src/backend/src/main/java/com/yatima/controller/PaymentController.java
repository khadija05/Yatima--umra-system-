package com.yatima.controller;
import com.yatima.model.Payment; import com.yatima.repository.PaymentRepository; import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/payments") @CrossOrigin("*")
public class PaymentController {
  private final PaymentRepository repo; private final JwtUtil jwt;
  public PaymentController(PaymentRepository r, JwtUtil j){repo=r;jwt=j;}
  @PostMapping public ResponseEntity<?> create(@RequestBody Payment p){
    if(p.getCreatedAt()==null) p.setCreatedAt(LocalDateTime.now());
    if(p.getDate()==null||p.getDate().isEmpty()) p.setDate(LocalDateTime.now().toString());
    if(p.getStatus()==null||p.getStatus().isEmpty()) p.setStatus("PENDING");
    if(p.getUsername()!=null && (p.getDonorName()==null||p.getDonorName().isEmpty())) p.setDonorName(p.getUsername());
    repo.save(p); return ResponseEntity.ok(Map.of("message","Malipo yametumwa. Khadija atathibitisha.","payment",p));
  }
  @GetMapping public ResponseEntity<?> all(@RequestHeader(value="Authorization",required=false) String auth){
    try{ String role=(String)jwt.parse(auth).get("role"); if("SUPER_ADMIN".equals(role)) return ResponseEntity.ok(repo.findAll()); String tenant=(String)jwt.parse(auth).get("tenantId"); return ResponseEntity.ok(repo.findByTenantId(tenant==null?"":tenant)); }catch(Exception e){ return ResponseEntity.status(403).body(Map.of("message","Ingia kwanza")); }
  }
}

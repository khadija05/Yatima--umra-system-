package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/donations") @CrossOrigin("*")
public class DonationController {
  private final DonationRepository repo; private final OrphanRepository orphanRepo; private final JwtUtil jwt;
  public DonationController(DonationRepository r, OrphanRepository o, JwtUtil j){repo=r;orphanRepo=o;jwt=j;}
  @PostMapping public ResponseEntity<?> create(@RequestBody Donation d){
    if(d.getCreatedAt()==null) d.setCreatedAt(LocalDateTime.now());
    if(d.getAmount()==null||d.getAmount()==0) d.setAmount(d.getKiasi());
    if(d.getKiasi()==null||d.getKiasi()==0) d.setKiasi(d.getAmount());
    if(d.getDonorName()==null||d.getDonorName().isEmpty()) d.setDonorName(d.getJinaMthamini());
    repo.save(d);
    // Update yatima otomatiki
    if(d.getOrphanId()!=null){ var o=orphanRepo.findById(d.getOrphanId()); if(o.isPresent()){ Orphan x=o.get(); x.setReceived((x.getReceived()==null?0:x.getReceived())+ (d.getAmount()==null?0:d.getAmount())); x.recalc(); orphanRepo.save(x); } }
    return ResponseEntity.ok(Map.of("message","Asante! Donation imetumwa."));
  }
  @GetMapping public ResponseEntity<?> all(@RequestHeader(value="Authorization",required=false) String auth){
    try{ String role=(String)jwt.parse(auth).get("role"); if("SUPER_ADMIN".equals(role)) return ResponseEntity.ok(repo.findAll()); String tenant=(String)jwt.parse(auth).get("tenantId"); return ResponseEntity.ok(repo.findByTenantId(tenant==null?"":tenant)); }catch(Exception e){ return ResponseEntity.status(403).body(Map.of("message","Ingia kwanza")); }
  }
}

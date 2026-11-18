package com.yatima.controller;
import com.yatima.model.Booking; import com.yatima.repository.BookingRepository; import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/bookings") @CrossOrigin("*")
public class BookingController {
  private final BookingRepository repo; private final JwtUtil jwt;
  public BookingController(BookingRepository r, JwtUtil j){repo=r;jwt=j;}
  @PostMapping public ResponseEntity<?> create(@RequestBody Booking b){ if(b.getCreatedAt()==null) b.setCreatedAt(LocalDateTime.now()); if(b.getStatus()==null) b.setStatus("PENDING"); if(b.getJinaPackage()==null) b.setJinaPackage(b.getJinaKifurushi()); repo.save(b); return ResponseEntity.ok(Map.of("message","Booking imetumwa")); }
  @GetMapping public ResponseEntity<?> all(@RequestHeader(value="Authorization",required=false) String auth){
    try{ String role=(String)jwt.parse(auth).get("role"); if("SUPER_ADMIN".equals(role)) return ResponseEntity.ok(repo.findAll()); String tenant=(String)jwt.parse(auth).get("tenantId"); return ResponseEntity.ok(repo.findByTenantId(tenant==null?"":tenant)); }catch(Exception e){ return ResponseEntity.status(403).body(Map.of("message","Ingia kwanza")); }
  }
}

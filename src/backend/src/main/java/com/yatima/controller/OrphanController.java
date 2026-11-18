package com.yatima.controller;
import com.yatima.model.Orphan; import com.yatima.repository.OrphanRepository; import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/orphans") @CrossOrigin("*")
public class OrphanController {
  private final OrphanRepository repo; private final JwtUtil jwt;
  public OrphanController(OrphanRepository r, JwtUtil j){repo=r;jwt=j;}
  @GetMapping("/featured") public ResponseEntity<?> featured(){ List<Orphan> l=repo.findByFeaturedTrue(); l.forEach(Orphan::recalc); return ResponseEntity.ok(l); }
  @GetMapping public ResponseEntity<?> all(@RequestHeader(value="Authorization",required=false) String auth){
    try{ String role=(String)jwt.parse(auth).get("role"); String tenant=(String)jwt.parse(auth).get("tenantId");
      if("SUPER_ADMIN".equals(role)) return ResponseEntity.ok(repo.findAll());
      return ResponseEntity.ok(repo.findByTenantId(tenant==null?"":tenant));
    }catch(Exception e){ return ResponseEntity.ok(repo.findByFeaturedTrue()); }
  }
  @PostMapping public ResponseEntity<?> create(@RequestBody Orphan o, @RequestHeader(value="Authorization",required=false) String auth){
    try{ String tenant=(String)jwt.parse(auth).get("tenantId"); if(o.getTenantId()==null||o.getTenantId().isEmpty()) o.setTenantId(tenant==null?"":tenant); }catch(Exception e){}
    o.recalc(); repo.save(o); return ResponseEntity.ok(o);
  }
  @PutMapping("/{id}/featured") public ResponseEntity<?> feat(@PathVariable Long id, @RequestHeader(value="Authorization",required=false) String auth){
    var o=repo.findById(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Orphan x=o.get(); x.setFeatured(!Boolean.TRUE.equals(x.getFeatured())); repo.save(x); return ResponseEntity.ok(x);
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> del(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.ok(Map.of("message","Amefutwa")); }
}

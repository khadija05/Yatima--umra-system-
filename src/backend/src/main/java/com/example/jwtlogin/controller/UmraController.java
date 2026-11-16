package com.example.jwtlogin.controller;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import com.example.jwtlogin.security.services.UserDetailsImpl; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/umra") @CrossOrigin(origins="*")
public class UmraController {
  @Autowired UmraRepository repo;
  private User me(Authentication a){ return ((UserDetailsImpl)a.getPrincipal()).getUser(); }
  @GetMapping public List<Umra> list(Authentication a){ return repo.findByOwnerEmail(me(a).getUsername()); }
  @PostMapping public ResponseEntity<?> add(@RequestBody Umra o, Authentication a){
    User u=me(a); Plan p=Plan.fromCode(u.getPlan()); long c=repo.findByOwnerEmail(u.getUsername()).size();
    if(c>=p.getMaxUmra()) return ResponseEntity.status(402).body(Map.of("message","Umefikia kikomo cha Umra"));
    o.setOwnerEmail(u.getUsername()); o.setOrgName(u.getOrgName()); return ResponseEntity.ok(repo.save(o));
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> del(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.ok(Map.of("message","Imefutwa")); }
}

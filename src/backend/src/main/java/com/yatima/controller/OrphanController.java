package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/orphans") @CrossOrigin("*")
public class OrphanController {
  @Autowired OrphanRepository repo; @Autowired UserRepository users;
  @GetMapping public List<Orphan> all(Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); return repo.findByOwnerId(u.id); }
  @PostMapping public ResponseEntity<?> add(@RequestBody Orphan o, Authentication auth){
    User u=users.findByUsername(auth.getName()).orElse(null);
    int max=u.plan.equals("DEMO")?10:u.plan.equals("KIDOGO")?50:u.plan.equals("KAWAIDA")?200:9999;
    if(repo.findByOwnerId(u.id).size()>=max) return ResponseEntity.status(403).body("Umemaliza kikomo cha Plan "+u.plan+"! Boresha Plan.");
    o.ownerId=u.id; o.ownerUsername=u.username; repo.save(o); return ResponseEntity.ok("Yatima ameongezwa!");
  }
}

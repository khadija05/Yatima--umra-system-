package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/orphans")
public class OrphanController { @Autowired OrphanRepository orphanRepo; @Autowired UserRepository userRepo;
@PostMapping public ResponseEntity<?> create(@RequestBody Orphan o, Authentication auth){
  User u=userRepo.findByUsername(auth.getName()).orElse(null); if(u==null) return ResponseEntity.status(401).body("Login kwanza");
  if(u.isLocked()) return ResponseEntity.status(403).body("🔒 Akaunti IMEFUNGWA! Lipa Plan yako "+u.getPlan()+" ili kuendelea.");
  Plan plan=Plan.fromCode(u.getPlan()); long count=orphanRepo.countByOwnerUsername(auth.getName());
  if(count >= plan.getMaxOrphans()) return ResponseEntity.status(403).body("Umefika mwisho wa Plan "+plan.getDisplayName()+" - Max Mayatima: "+plan.getMaxOrphans()+". Boresha Plan!");
  o.setOwnerUsername(auth.getName()); o.setOrgName(u.getOrgName()); return ResponseEntity.ok(orphanRepo.save(o));
}
@GetMapping public List<Orphan> my(Authentication auth){ return orphanRepo.findByOwnerUsername(auth.getName()); }
@GetMapping("/all") public List<Orphan> all(){ return orphanRepo.findAll(); }
@DeleteMapping("/{id}") public ResponseEntity<?> del(@PathVariable Long id){ orphanRepo.deleteById(id); return ResponseEntity.ok().build(); }
}

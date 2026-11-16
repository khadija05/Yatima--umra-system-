package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/umra")
public class UmraController { @Autowired UmraRepository repo; @Autowired UserRepository userRepo;
@PostMapping public ResponseEntity<?> create(@RequestBody Umra u, Authentication a){
  User user=userRepo.findByUsername(a.getName()).orElse(null); if(user!=null){ Plan plan=Plan.fromCode(user.getPlan()); long c=repo.findByOwnerUsername(a.getName()).size(); if(c>=plan.getMaxUmra()) return ResponseEntity.status(403).body("Umefika mwisho wa Plan "+plan.getDisplayName()+" - Max Umra: "+plan.getMaxUmra()); }
  u.setOwnerUsername(a.getName()); if(u.getIdadiWatu()==null) u.setIdadiWatu(0); if(u.getUwezoKuchukuaWatu()!=null) u.setNafasiZilizobaki(u.getUwezoKuchukuaWatu()-u.getIdadiWatu()); return ResponseEntity.ok(repo.save(u));
}
@GetMapping public List<Umra> all(@RequestParam(required=false) String mkoa){ if(mkoa!=null) return repo.findByMkoa(mkoa); return repo.findAll(); }
@GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
@GetMapping("/ranking/bora-zaidi") public List<Umra> bora(){ return repo.findTopBoraZaidi(); }
@GetMapping("/ranking/bei-nafuu") public List<Umra> bei(){ return repo.findTopByBei(); }
}

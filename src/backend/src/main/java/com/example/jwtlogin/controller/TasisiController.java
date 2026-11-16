package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.Tasisi; import com.example.jwtlogin.repository.TasisiRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/tasisi")
public class TasisiController { @Autowired TasisiRepository repo;
@PostMapping public Tasisi create(@RequestBody Tasisi t, Authentication a){ t.setOwnerUsername(a.getName()); return repo.save(t); }
@GetMapping public List<Tasisi> all(@RequestParam(required=false) String mkoa){ if(mkoa!=null) return repo.findByMkoa(mkoa); return repo.findAll(); }
@GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
@GetMapping("/ranking/bora-zaidi") public List<Tasisi> bora(){ return repo.findTopBoraZaidi(); }
@PostMapping("/{id}/rate") public ResponseEntity<?> rate(@PathVariable Long id, @RequestParam Double nyota){ return repo.findById(id).map(t->{ double jumla=(t.getWastaniRating()==null?0:t.getWastaniRating())*(t.getIdadiWaliotathmini()==null?0:t.getIdadiWaliotathmini()); int idadi=(t.getIdadiWaliotathmini()==null?0:t.getIdadiWaliotathmini())+1; t.setIdadiWaliotathmini(idadi); t.setWastaniRating((jumla+nyota)/idadi); return ResponseEntity.ok(repo.save(t)); }).orElse(ResponseEntity.notFound().build()); }
}

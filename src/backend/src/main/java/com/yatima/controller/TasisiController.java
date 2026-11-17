package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/tasisi") @CrossOrigin("*")
public class TasisiController {
  @Autowired TasisiRepository repo; @Autowired UserRepository users;
  @GetMapping public List<Tasisi> all(){ return repo.findAll(); }
  @GetMapping("/ranking/bora-zaidi") public List<Tasisi> bora(){ return repo.findBoraZaidi(); }
  @PostMapping public ResponseEntity<?> add(@RequestBody Tasisi t, Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); t.ownerId=u.id; repo.save(t); return ResponseEntity.ok("Taasisi imesajiliwa!"); }
  @PostMapping("/{id}/rate") public ResponseEntity<?> rate(@PathVariable Long id, @RequestParam int nyota){ Tasisi t=repo.findById(id).orElse(null); if(t==null) return ResponseEntity.notFound().build(); t.jumlaRating+=nyota; t.idadiRating++; t.wastaniRating=(double)t.jumlaRating/t.idadiRating; repo.save(t); return ResponseEntity.ok("Asante!"); }
}

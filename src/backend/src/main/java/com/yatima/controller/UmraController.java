package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/umra") @CrossOrigin("*")
public class UmraController {
  @Autowired UmraRepository repo; @Autowired UserRepository users;
  @GetMapping public List<Umra> all(){ return repo.findAll(); }
  @PostMapping public ResponseEntity<?> add(@RequestBody Umra x, Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); x.ownerId=u.id; repo.save(x); return ResponseEntity.ok("Umra imesajiliwa!"); }
}

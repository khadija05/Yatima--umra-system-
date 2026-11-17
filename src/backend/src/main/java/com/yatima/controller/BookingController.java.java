package com.yatima.controller;
import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/bookings") @CrossOrigin("*")
public class BookingController {
  @Autowired BookingRepository repo; @Autowired PackageRepository pkgs; @Autowired UserRepository users;
  @PostMapping("/kodisha/{id}") public ResponseEntity<?> kodisha(@PathVariable Long id, @RequestParam(defaultValue="1") int miezi, Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); Package p=pkgs.findById(id).orElse(null); Booking b=new Booking(); b.userId=u.id; b.username=u.username; b.packageId=p.id; b.jinaPackage=p.jinaPackage; b.bei=p.bei*miezi; b.miezi=miezi; b.tareheMwisho=LocalDate.now().plusMonths(miezi); repo.save(b); return ResponseEntity.ok("Umekodisha "+p.jinaPackage+" kwa miezi "+miezi); }
  @GetMapping("/yangu") public List<Booking> yangu(Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); return repo.findByUserId(u.id); }
  @GetMapping("/yangu/status") public Object status(Authentication auth){ User u=users.findByUsername(auth.getName()).orElse(null); if(u.subscriptionExpiry!=null && u.subscriptionExpiry.isBefore(java.time.LocalDateTime.now())) return ResponseEntity.status(403).body("IMEFUNGWA! Lipa ili kufungua."); return repo.findByUserId(u.id); }
  @PostMapping("/{id}/lipa") public ResponseEntity<?> lipa(@PathVariable Long id, @RequestParam String njia, @RequestParam String muamala){ Booking b=repo.findById(id).orElse(null); b.imelipa=true; b.status="ACTIVE"; repo.save(b); return ResponseEntity.ok("Umelipa!"); }
}

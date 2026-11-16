package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.Donation; import com.example.jwtlogin.repository.DonationRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*; import java.util.List;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/donations")
public class DonationController { @Autowired DonationRepository repo;
@PostMapping public Donation create(@RequestBody Donation d){ d.setStatus("PENDING"); return repo.save(d); }
@GetMapping public List<Donation> all(){ return repo.findAll(); }
@GetMapping("/tasisi/{id}") public List<Donation> byTasisi(@PathVariable Long id){ return repo.findByTasisiId(id); }
}

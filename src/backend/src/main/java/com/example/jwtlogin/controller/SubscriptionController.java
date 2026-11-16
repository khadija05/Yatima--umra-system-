package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.Subscription; import com.example.jwtlogin.repository.SubscriptionRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/subscriptions")
public class SubscriptionController { @Autowired SubscriptionRepository repo;
@PostMapping public Subscription create(@RequestBody Subscription s, Authentication a){ s.setUsername(a.getName()); Double kiasi=s.getKiasi(); Integer miezi=s.getMiezi(); Double jumla=kiasi * miezi.doubleValue(); s.setJumla(jumla); s.setStatus("ACTIVE"); return repo.save(s); }
@GetMapping("/yangu") public List<Subscription> yangu(Authentication a){ return repo.findByUsername(a.getName()); }
}

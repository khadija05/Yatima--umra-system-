package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.PackageKukodisha; import com.example.jwtlogin.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@CrossOrigin(origins="*", maxAge=3600) @RestController @RequestMapping("/api/packages")
public class PackageController { @Autowired PackageRepository repo; @Autowired TasisiRepository tasisiRepo;
@PostMapping public PackageKukodisha create(@RequestBody PackageKukodisha p, Authentication a){ p.setOwnerUsername(a.getName()); if(p.getTasisiId()!=null) tasisiRepo.findById(p.getTasisiId()).ifPresent(t->p.setJinaTasisi(t.getJinaTasisi())); if(p.getNafasiZilizobaki()==null) p.setNafasiZilizobaki(p.getUwezoWatu()); return repo.save(p); }
@GetMapping public List<PackageKukodisha> all(@RequestParam(required=false) String aina){ if(aina!=null) return repo.findByAina(aina); return repo.findAll(); }
@GetMapping("/umra") public List<PackageKukodisha> umra(){ return repo.findByAina("UMRA"); }
@GetMapping("/mayatima") public List<PackageKukodisha> mayatima(){ return repo.findByAina("MAYATIMA"); }
@DeleteMapping("/{id}") public ResponseEntity<?> del(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.ok().build(); }
}

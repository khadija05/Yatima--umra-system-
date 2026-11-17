package com.yatima.controller;
import com.yatima.model.YatimaPackage; import com.yatima.repository.PackageRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*; import java.util.*; import jakarta.annotation.PostConstruct;
@RestController @RequestMapping("/api/packages") @CrossOrigin("*")
public class PackageController {
  @Autowired PackageRepository repo;
  @PostConstruct public void seed(){ if(repo.count()==0){ YatimaPackage p1=new YatimaPackage(); p1.jinaPackage="Umra Economy"; p1.aina="UMRA"; p1.bei=4500000.0; p1.maelezo="Siku 14, Hoteli Makkah"; repo.save(p1); YatimaPackage p2=new YatimaPackage(); p2.jinaPackage="Msaada wa Mayatima - Mwezi"; p2.aina="MAYATIMA"; p2.bei=50000.0; p2.maelezo="Chakula + Shule"; repo.save(p2); } }
  @GetMapping public List<YatimaPackage> all(@RequestParam(required=false) String aina){ if(aina!=null && !aina.isEmpty()) return repo.findByAina(aina); return repo.findAll(); }
}

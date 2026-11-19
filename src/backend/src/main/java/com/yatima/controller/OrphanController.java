

// OrphanController.java - Admin anachagua Package ya Yatima
package com.yatima.controller; import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/orphans") @CrossOrigin("*") public class OrphanController {
  private final OrphanRepository orphans; private final YatimaPackageRepository packages; public OrphanController(OrphanRepository o, YatimaPackageRepository p){this.orphans=o; this.packages=p;}
  @GetMapping public List<Orphan> all(){ return orphans.findAll(); }
  @PostMapping public Orphan create(@RequestBody Orphan o){ if(o.getTargetAmount()==null) o.setTargetAmount(4500000L); return orphans.save(o); }
  @GetMapping("/{id}") public Orphan one(@PathVariable Long id){ return orphans.findById(id).orElseThrow(); }
  @PutMapping("/{id}") public Orphan upd(@PathVariable Long id,@RequestBody Orphan n){ Orphan o=orphans.findById(id).orElseThrow(); o.setName(n.getName()); o.setCollectedAmount(n.getCollectedAmount()); o.setGuardianName(n.getGuardianName()); o.setGuardianPhone(n.getGuardianPhone()); o.setPhotoUrl(n.getPhotoUrl()); return orphans.save(o); }
  @PutMapping("/{id}/choose-package/{packageId}") public Orphan choosePackage(@PathVariable Long id,@PathVariable Long packageId){ Orphan o=orphans.findById(id).orElseThrow(); YatimaPackage p=packages.findById(packageId).orElseThrow(); o.setYatimaPackageId(p.getId()); o.setYatimaPackageName(p.getName()); o.setYatimaPackagePrice(p.getPrice()); o.setYatimaPackageStatus("AMECHAGULIWA"); o.setTargetAmount(p.getPrice()); return orphans.save(o); }
  @DeleteMapping("/{id}") public void del(@PathVariable Long id){ orphans.deleteById(id); }
}

// 
// 

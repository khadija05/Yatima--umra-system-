// 
// 
// DonationController.java
package com.yatima.controller; import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/donations") @CrossOrigin("*") public class DonationController {
  private final DonationRepository donations; private final OrphanRepository orphans; public DonationController(DonationRepository d, OrphanRepository o){this.donations=d; this.orphans=o;}
  @GetMapping public List<Donation> all(){ return donations.findAll(); }
  @PostMapping public Donation create(@RequestBody Donation d){
    d.setStatus("PENDING"); d.setPaymentStatus("PENDING"); d.setPaymentDate(java.time.LocalDate.now().toString()); d.setReceiptNumber("YT-2026-"+String.format("%04d",donations.count()+1));
    if(d.getOrphanId()!=null){ Orphan o=orphans.findById(d.getOrphanId()).orElse(null); if(o!=null){ d.setOrphanName(o.getName()); d.setOrphanPhotoUrl(o.getPhotoUrl()); d.setInstitutionName(o.getInstitutionName()); d.setInstitutionPbzAccount(o.getInstitutionPbzAccount()); d.setInstitutionPbzName(o.getInstitutionPbzName()); d.setInstitutionMpesa("0778709707"); d.setInstitutionTigo("0715709707"); d.setInstitutionAirtel("0788709707"); d.setYatimaPackageName(o.getYatimaPackageName()); } }
    return donations.save(d);
  }
  @PutMapping("/{id}/confirm") public Donation confirm(@PathVariable Long id){ Donation d=donations.findById(id).orElseThrow(); if("CONFIRMED".equals(d.getStatus())) return d; d.setStatus("CONFIRMED"); d.setPaymentStatus("CONFIRMED"); donations.save(d); if(d.getOrphanId()!=null){ Orphan o=orphans.findById(d.getOrphanId()).orElse(null); if(o!=null){ o.setCollectedAmount(o.getCollectedAmount()+d.getAmount()); orphans.save(o);} } return d; }
  @PutMapping("/{id}/reject") public Donation reject(@PathVariable Long id){ Donation d=donations.findById(id).orElseThrow(); d.setStatus("REJECTED"); d.setPaymentStatus("REJECTED"); return donations.save(d); }
}

// 

package com.yatima.controller;
import com.yatima.model.Donation;
import com.yatima.repository.DonationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/donations") @CrossOrigin("*")
public class DonationController {
  private final DonationRepository repo; public DonationController(DonationRepository r){this.repo=r;}
  @GetMapping public List<Donation> all(){return repo.findAll();}
  @PostMapping public Donation create(@RequestBody Donation d){if(d.getPbzName()==null||d.getPbzName().isEmpty()) d.setPbzName("KHADIJA DENGE"); return repo.save(d);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

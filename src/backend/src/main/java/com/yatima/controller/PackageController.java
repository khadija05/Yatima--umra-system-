package com.yatima.controller;
import com.yatima.model.Package;
import com.yatima.repository.PackageRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/packages") @CrossOrigin("*")
public class PackageController {
  private final PackageRepository repo; public PackageController(PackageRepository r){this.repo=r;}
  @GetMapping public List<Package> all(){return repo.findAll();}
  @PostMapping public Package create(@RequestBody Package p){return repo.save(p);}
  @GetMapping("/{code}") public Package byCode(@PathVariable String code){return repo.findByCode(code).orElse(null);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

package com.yatima.controller;
import com.yatima.model.Institution;
import com.yatima.repository.InstitutionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/institutions")
@CrossOrigin("*")
public class InstitutionController {
  private final InstitutionRepository repo;
  public InstitutionController(InstitutionRepository r){ this.repo = r; }
  @GetMapping public List<Institution> all(){ return repo.findAll(); }
  @PostMapping public Institution create(@RequestBody Institution i){ return repo.save(i); }
  @GetMapping("/{id}") public Institution one(@PathVariable Long id){ return repo.findById(id).orElseThrow(); }
  @PutMapping("/{id}") public Institution update(@PathVariable Long id, @RequestBody Institution in){
    Institution e = repo.findById(id).orElseThrow();
    e.setName(in.getName());
    e.setLocation(in.getLocation());
    e.setPhone(in.getPhone());
    e.setDescription(in.getDescription());
    return repo.save(e);
  }
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ repo.deleteById(id); }
}

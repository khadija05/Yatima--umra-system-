InstitutionController.java
package com.yatima.controller; import com.yatima.model.Institution; import com.yatima.repository.InstitutionRepository; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/institutions") @CrossOrigin("*") public class InstitutionController {
  private final InstitutionRepository repo; public InstitutionController(InstitutionRepository r){this.repo=r;}
  @GetMapping public List<Institution> all(){ return repo.findAll(); }
  @PostMapping public Institution create(@RequestBody Institution i){ return repo.save(i); }
  @GetMapping("/{id}") public Institution one(@PathVariable Long id){ return repo.findById(id).orElseThrow(); }
}

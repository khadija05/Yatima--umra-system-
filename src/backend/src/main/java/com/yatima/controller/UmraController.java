package com.yatima.controller;
import com.yatima.model.Umra;
import com.yatima.repository.UmraRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/umra") @CrossOrigin("*")
public class UmraController {
  private final UmraRepository repo; public UmraController(UmraRepository r){this.repo=r;}
  @GetMapping public List<Umra> all(){return repo.findAll();}
  @PostMapping public Umra create(@RequestBody Umra u){return repo.save(u);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

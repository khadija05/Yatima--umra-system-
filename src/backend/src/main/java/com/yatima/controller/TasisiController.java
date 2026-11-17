package com.yatima.controller;
import com.yatima.model.Tasisi;
import com.yatima.repository.TasisiRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/tasisi") @CrossOrigin("*")
public class TasisiController {
  private final TasisiRepository repo; public TasisiController(TasisiRepository r){this.repo=r;}
  @GetMapping public List<Tasisi> all(){return repo.findAll();}
  @PostMapping public Tasisi create(@RequestBody Tasisi t){return repo.save(t);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

package com.yatima.controller;
import com.yatima.model.Payment;
import com.yatima.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/payments") @CrossOrigin("*")
public class PaymentController {
  private final PaymentRepository repo; public PaymentController(PaymentRepository r){this.repo=r;}
  @GetMapping public List<Payment> all(){return repo.findAll();}
  @PostMapping public Payment create(@RequestBody Payment p){return repo.save(p);}
  @GetMapping("/user/{username}") public List<Payment> byUser(@PathVariable String username){return repo.findByUsername(username);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

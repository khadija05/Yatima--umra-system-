package com.yatima.controller;
import com.yatima.model.Donation; import com.yatima.repository.DonationRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/donations") @CrossOrigin("*")
public class DonationController { @Autowired DonationRepository repo; @PostMapping public Donation add(@RequestBody Donation d){ return repo.save(d); } @GetMapping public List<Donation> all(){ return repo.findAll(); } }

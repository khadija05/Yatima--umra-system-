package com.yatima.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin("*")
public class StatsController {

  @GetMapping("/stats")
  public Map<String,Object> stats(){
    return Map.of(
      "orphans", 0,
      "donations", 0,
      "umra", 0,
      "totalDonations", 0,
      "totalSponsorship", 0,
      "status", "OK"
    );
  }

  @GetMapping("/health")
  public Map<String,Object> health(){
    return Map.of("status","OK","message","Backend iko hai");
  }

  @GetMapping("/")
  public Map<String,Object> root(){
    return Map.of("status","OK","message","Yatima Umra Backend iko hai");
  }
}

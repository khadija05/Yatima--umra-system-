package com.yatima.controller;
import com.yatima.repository.PackageRepository; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/packages") @CrossOrigin("*")
public class PackageController {
  private final PackageRepository repo; public PackageController(PackageRepository r){repo=r;}
  @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(repo.findAllByOrderByOrderNoAsc()); }
}

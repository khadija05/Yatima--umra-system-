package com.yatima.controller;

import com.yatima.model.YatimaPackage;
import com.yatima.repository.YatimaPackageRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin("*")
public class YatimaPackageController {

    private final YatimaPackageRepository repo;

    public YatimaPackageController(YatimaPackageRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<YatimaPackage> all() {
        return repo.findAll();
    }

    @PostMapping
    public YatimaPackage create(@RequestBody YatimaPackage p) {
        return repo.save(p);
    }
}

package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.PackageKukodisha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PackageRepository extends JpaRepository<PackageKukodisha, Long> {
  List<PackageKukodisha> findByAina(String aina);
  List<PackageKukodisha> findByTasisiId(Long tasisiId);
}

package com.yatima.repository;
import com.yatima.model.Package; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*; public interface PackageRepository extends JpaRepository<Package,Long>{
  Optional<Package> findByCode(String c); List<Package> findAllByOrderByOrderNoAsc();
}

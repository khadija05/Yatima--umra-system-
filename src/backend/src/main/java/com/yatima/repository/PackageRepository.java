package com.yatima.repository;
import com.yatima.model.Package; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface PackageRepository extends JpaRepository<Package,Long>{ List<Package> findByAina(String aina); }

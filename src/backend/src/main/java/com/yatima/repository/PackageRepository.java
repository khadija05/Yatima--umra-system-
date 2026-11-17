package com.yatima.repository;
import com.yatima.model.YatimaPackage; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface PackageRepository extends JpaRepository<YatimaPackage,Long>{ List<YatimaPackage> findByAina(String aina); }

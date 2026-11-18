package com.yatima.repository;
import com.yatima.model.Donation; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*; public interface DonationRepository extends JpaRepository<Donation,Long>{
  List<Donation> findByTenantId(String t); List<Donation> findByOrphanId(Long id);
}

package com.yatima.repository;
import com.yatima.model.Payment; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*; public interface PaymentRepository extends JpaRepository<Payment,Long>{
  List<Payment> findByUsername(String u); List<Payment> findByTenantId(String t);
  List<Payment> findByStatus(String s);
}

package com.yatima.repository;
import com.yatima.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByUsername(String username);
  List<Payment> findByTenantId(String tenantId);
}

package com.yatima.repository;
import com.yatima.model.User; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*; public interface UserRepository extends JpaRepository<User,Long>{
  Optional<User> findByUsername(String u); Optional<User> findByEmail(String e);
  List<User> findByTenantId(String t); Optional<User> findByResetToken(String t);
}

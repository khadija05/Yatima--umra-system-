
// OrphanRepository.java
package com.example.jwtlogin.repository; import com.example.jwtlogin.model.Orphan; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; public interface OrphanRepository extends JpaRepository<Orphan, Long> { List<Orphan> findByOwnerUsername(String u); long countByOwnerUsername(String u); }
 

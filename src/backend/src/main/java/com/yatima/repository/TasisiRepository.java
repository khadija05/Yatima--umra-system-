package com.yatima.repository;
import com.yatima.model.Tasisi; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List; import org.springframework.data.jpa.repository.Query;
public interface TasisiRepository extends JpaRepository<Tasisi,Long>{
  @Query("SELECT t FROM Tasisi t ORDER BY t.wastaniRating DESC, t.idadiWatu DESC") List<Tasisi> findBoraZaidi();
}

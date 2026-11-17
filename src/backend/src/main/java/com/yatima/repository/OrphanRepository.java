package com.yatima.repository;
import com.yatima.model.Orphan; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface OrphanRepository extends JpaRepository<Orphan,Long>{ List<Orphan> findByOwnerId(Long id); }

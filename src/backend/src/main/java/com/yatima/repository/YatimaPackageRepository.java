.springframework.data.jpa.repository.JpaRepository; public interface DonationRepository extends JpaRepository<Donation,Long>{}
package com.yatima.repository; import com.yatima.model.YatimaPackage; import org.springframework.data.jpa.repository.JpaRepository; public interface YatimaPackageRepository extends JpaRepository<YatimaPackage,Long>{}

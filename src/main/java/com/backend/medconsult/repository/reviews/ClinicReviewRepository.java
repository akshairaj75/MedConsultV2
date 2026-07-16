package com.backend.medconsult.repository.reviews;

import com.backend.medconsult.entity.reviews.ClinicReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ClinicReviewRepository extends JpaRepository<ClinicReview, UUID> {
}

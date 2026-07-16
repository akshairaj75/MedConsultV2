package com.backend.medconsult.repository.platformAndCompliance;

import com.backend.medconsult.entity.platformAndCompliance.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLog, UUID> {
}

package com.backend.medconsult.repository.platformAndCompliance;

import com.backend.medconsult.entity.platformAndCompliance.PrivacySetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PrivacySettingRepository extends JpaRepository<PrivacySetting, UUID> {
}

package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.ClinicBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ClinicBranchRepository extends JpaRepository<ClinicBranch, UUID> {
}

package com.backend.medconsult.repository.references;

import com.backend.medconsult.entity.references.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
}

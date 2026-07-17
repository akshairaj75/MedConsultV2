package com.backend.medconsult.repository.references;

import com.backend.medconsult.entity.references.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, UUID> {

    List<Locality> findByCity_CityId(UUID cityId);
}

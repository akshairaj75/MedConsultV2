package com.backend.medconsult.serviceImpl.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.references.CityRequestDto;
import com.backend.medconsult.dto.references.CityResponseDto;
import com.backend.medconsult.dto.references.LocalityRequestDto;
import com.backend.medconsult.dto.references.LocalityResponseDto;
import com.backend.medconsult.entity.references.City;
import com.backend.medconsult.entity.references.Locality;
import com.backend.medconsult.repository.references.CityRepository;
import com.backend.medconsult.repository.references.LocalityRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.CityService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    LocalityRepository localityRepository;

    @Override
    public List<CityResponseDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream().map(CityResponseDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public CityResponseDto addCity(CityRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {

        City city = new City();
        city.setCountryCode(dto.getCountryCode());
        city.setNameEn(dto.getNameEn());
        city.setNameAr(dto.getNameAr());
        city.setSortOrder(dto.getSortOrder());
        city.setIsActive(dto.getIsActive());
        return CityResponseDto.fromEntity(cityRepository.save(city));
    }

    @Transactional
    @Override
    public CityResponseDto updateCity(UUID cityId, CityRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        if (dto.getCountryCode() != null) {
            city.setCountryCode(dto.getCountryCode());
        }
        if (dto.getNameEn() != null) {
            city.setNameEn(dto.getNameEn());
        }
        if (dto.getNameAr() != null) {
            city.setNameAr(dto.getNameAr());
        }
        if (dto.getSortOrder() != null) {
            city.setSortOrder(dto.getSortOrder());
        }
        if (dto.getIsActive() != null) {
            city.setIsActive(dto.getIsActive());
        }
        return CityResponseDto.fromEntity(cityRepository.save(city));
    }

    @Transactional
    @Override
    public String deleteCity(UUID cityId, CustomUserPrincipal authUser, HttpServletRequest request) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        city.setIsActive(false);
        cityRepository.save(city);
        return "City deleted successfully";
    }

    @Override
    public CityResponseDto getCity(UUID cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return CityResponseDto.fromEntity(city);
    }

    @Override
    public List<LocalityResponseDto> getLocalities(UUID cityId) {
        List<Locality> localities = localityRepository.findByCity_CityId(cityId);
        return localities.stream().map(LocalityResponseDto::fromEntity).toList();
    }

    @Override
    public LocalityResponseDto getLocality(UUID localityId) {
        Locality locality = localityRepository.findById(localityId)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        return LocalityResponseDto.fromEntity(locality);
    }

    @Override
    @Transactional
    public LocalityResponseDto addLocality(LocalityRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        Locality locality = new Locality();
        locality.setCity(city);
        locality.setNameEn(dto.getNameEn());
        locality.setNameAr(dto.getNameAr());
        locality.setPostalCode(dto.getPostalCode());
        locality.setLatitude(dto.getLatitude());
        locality.setLongitude(dto.getLongitude());
        locality.setIsActive(dto.getIsActive());
        return LocalityResponseDto.fromEntity(localityRepository.save(locality));
    }

    @Override
    @Transactional
    public LocalityResponseDto updateLocality(UUID localityId, LocalityRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Locality locality = localityRepository.findById(localityId).get();
        
        if (dto.getPostalCode() != null) {
            locality.setPostalCode(dto.getPostalCode());
        }
        if (dto.getNameAr() != null) {
            locality.setNameAr(dto.getNameAr());
        }
        if (dto.getNameEn() != null) {
            locality.setNameEn(dto.getNameEn());
        }
        if (dto.getLatitude() != null) {
            locality.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            locality.setLongitude(dto.getLongitude());
        }
        if (dto.getIsActive() != null) {
            locality.setIsActive(dto.getIsActive());
        }
        if (dto.getCityId() != null) {
            City city = cityRepository.findById(dto.getCityId()).get();
            locality.setCity(city);
        }
        return LocalityResponseDto.fromEntity(localityRepository.save(locality));
    }

    @Override
    @Transactional
    public String deleteLocality(UUID localityId, CustomUserPrincipal authUser, HttpServletRequest request) {
        Locality locality = localityRepository.findById(localityId).get();
        localityRepository.delete(locality);
        return "Locality deleted successfully";
    }

}

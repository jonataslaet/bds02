package com.devsuperior.bds02.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.exceptions.DatabaseException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly=true)
	public City findById(Long id) {
		Optional<City> cityOptional = cityRepository.findById(id);
		cityOptional.orElseThrow(() -> new EntityNotFoundException("City not found"));
		return cityOptional.get();
	}
	
	public CityDTO update(Long id, CityDTO cityDTO) {
		City savedCity = findById(id);
		savedCity = cityRepository.save(new City(id, cityDTO.getName()));
		return new CityDTO(savedCity);
	}

	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("City not found");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("City depends on other entity");
		}
	}

//	public Page<CityDTO> findAllPaged(Pageable pageable) {
//		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
//		Page<City> findAll = cityRepository.findAll(pageable);
//		return findAll.map(c -> new CityDTO(c.getId(), c.getName()));
//	}

	public List<CityDTO> findAllSortedByName() {
		List<City> findAll = cityRepository.findAll(Sort.by("name"));
		return findAll.stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
	}
}

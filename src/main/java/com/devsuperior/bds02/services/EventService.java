package com.devsuperior.bds02.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;	

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly=true)
	public Event findById(Long id) {
		Optional<Event> eventOptional = eventRepository.findById(id);
		eventOptional.orElseThrow(() -> new EntityNotFoundException("Event not found"));
		return eventOptional.get();
	}
	
	public EventDTO update(Long id, EventDTO eventDTO) {
		Event savedEvent = findById(id);
		savedEvent = eventRepository.save(new Event(id, eventDTO.getName(), eventDTO.getDate(), eventDTO.getUrl(), new City(eventDTO.getCityId(), null)));
		return new EventDTO(savedEvent);
	}
	
}

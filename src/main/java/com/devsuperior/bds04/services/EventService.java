package com.devsuperior.bds04.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.respositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPaged(Pageable pageable) {
		Page<Event> list = repository.findAll(pageable);
		return list.map(x -> new EventDTO(x));
	}

	@Transactional(readOnly = true)
	public EventDTO findById(Long id) {
		try {
		Optional<Event> obj = repository.findById(id);
		Event entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new EventDTO(entity);
		
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new EventDTO(entity);
	}
	
}

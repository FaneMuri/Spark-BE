package com.example.spark.service;

import com.example.spark.controller.EventRequest;
import com.example.spark.model.Event;
import com.example.spark.model.User;
import com.example.spark.repository.EventRepository;
import com.example.spark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(EventRequest eventRequest) {


        User organizer = userRepository.findById(eventRequest.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));

        Event event = new Event();
        event.setDescription(eventRequest.getDescription());
        event.setDate(eventRequest.getDate());
        event.setParticipantCount(eventRequest.getParticipantCount());
        event.setLocation(eventRequest.getLocation());
        event.setImage(eventRequest.getImage());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

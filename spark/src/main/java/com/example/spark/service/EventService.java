package com.example.spark.service;

import com.example.spark.controller.EventRequest;
import com.example.spark.controller.EventResponse;
import com.example.spark.model.Event;
import com.example.spark.model.Role;
import com.example.spark.model.User;
import com.example.spark.repository.EventRepository;
import com.example.spark.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<EventRequest> findAllEvents() {
        var events =  eventRepository.findAll();
        List<EventRequest> eventRequests = new ArrayList<>();
        for (var event : events) {
            var participanti = event.getParticipants();
            List<Long> participants = new ArrayList<>();
            for(var participant : participanti) {
                participants.add(participant.getId());
            }
            EventRequest eq = new EventRequest(event.getId(),event.getName(),event.getDescription(),event.getDate(),
                    event.getParticipantCount(), event.getLocation(), event.getOrganizer() ,event.getImage(),participants);
            eventRequests.add(eq);
        }
        return eventRequests;
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(EventResponse eventRequest) {


        /*User organizer = userRepository.findById(eventRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));*/

        Event event = new Event();
        event.setDescription(eventRequest.getDescription());
        event.setDate(eventRequest.getDate());
        event.setParticipantCount(eventRequest.getParticipantCount());
        event.setLocation(eventRequest.getLocation());
        event.setImage(Base64.getDecoder().decode(eventRequest.getImage()));
        event.setName(eventRequest.getName());
        User organizer = userRepository.findById(eventRequest.getOrganizer().getId()).get();
        event.setOrganizer(organizer);
        event.setParticipants(null);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void joinEvent(Long idEvent, Long userId) {
        var user = userRepository.findById(userId).get();
        var event = findEventById(idEvent).get();
        var part = event.getParticipants();
        part.add(user);
        event.setParticipants(part);
        event.setParticipantCount(event.getParticipantCount() + 1);
        eventRepository.save(event);

    }
}

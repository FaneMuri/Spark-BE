package com.example.spark.controller;

import com.example.spark.model.Event;
import com.example.spark.service.EventService;
import com.example.spark.service.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final JwtService jwtService;

    @Autowired
    public EventController(EventService eventService, JwtService jwtService) {
        this.eventService = eventService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<EventRequest> getAllEvents(
            @RequestHeader("Authorization") String auth
    ) {
        // This is how you get the user id
        // vvvvv
        System.out.println(jwtService.extractIdFromAuthorization(auth));
        return eventService.findAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.findEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<HttpStatus> createEvent(@RequestBody EventResponse eventRequest) {

        Event event = eventService.saveEvent(eventRequest);
        if (event != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/join/{id}/{idU}")
    public ResponseEntity<HttpStatus> postEvent(@PathVariable Long id, @PathVariable Long idU) {
        eventService.joinEvent(id,idU);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

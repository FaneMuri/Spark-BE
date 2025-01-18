package com.example.spark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "participantcount")
    private Long participantCount;

    @Column(name = "location")
    private String location;

    @Column(name = "image")
    private byte[] image;

    // Relationship with User (organizer)
    @ManyToOne
    @JoinColumn(name = "organizerid", nullable = false)
    private User organizer;

    // Relationship with Post
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    // Many-to-Many Relationship with Users (Participants)
    @ManyToMany
    @JoinTable(
            name = "event_participants",  // Join table name
            joinColumns = @JoinColumn(name = "event_id"),  // FK for Event
            inverseJoinColumns = @JoinColumn(name = "user_id")  // FK for User
    )
    @JsonIgnore
    private List<User> participants;
}
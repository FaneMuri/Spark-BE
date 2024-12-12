package com.example.spark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    // Relationship with Event
    @ManyToOne
    @JoinColumn(name = "eventid", nullable = false)
    @JsonIgnore
    private Event event;

    // Relationship with Comment
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentTask> comments;

    // Relationship with User
    @ManyToMany(mappedBy = "tasks")
    @JsonIgnore
    private List<User> users;
}

package com.example.spark.model.DTO;

import com.example.spark.model.Event;
import com.example.spark.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {
    public String name;
    public String description;
    public String status;
    public LocalDateTime deadline;
    public Event event;
    public User user;
    public Long eventID;

    public TaskDTO(String status, Long eventID) {
        this.status = status;
        this.eventID = eventID;
    }

    public TaskDTO(String name, String description, LocalDateTime deadline, Long eventID) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.event = null;
        this.user = null;
        this.eventID = eventID;
    }

    public TaskDTO(String name, String status){
        this.name = name;
        this.status = status;
    }
}

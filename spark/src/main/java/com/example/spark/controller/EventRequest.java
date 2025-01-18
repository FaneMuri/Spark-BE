package com.example.spark.controller;

import com.example.spark.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventRequest implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Timestamp date;
    private Long participantCount;
    private String location;
    private User organizer;
    private byte[] image;
    private List<Long> participants;
}
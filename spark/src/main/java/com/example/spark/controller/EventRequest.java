package com.example.spark.controller;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class EventRequest {
    private String description;
    private Timestamp date;
    private Long participantCount;
    private String location;
    private byte[] image;
    private Long organizerId;
}
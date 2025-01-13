package com.example.spark.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class EventRequest implements Serializable {
    private Long id ;
    private String name;
    private String description;
    private Timestamp date;
    private Long participantCount;
    private String location;
    private String image;
}
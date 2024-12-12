package com.example.spark.controller;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TaskRequest {
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Timestamp taskDeadline;
    private Long eventID;
    private List<Long> commentIDs;
    private List<Long> userIDs;
}

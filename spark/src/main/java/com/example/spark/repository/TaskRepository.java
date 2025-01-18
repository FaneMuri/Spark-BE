package com.example.spark.repository;

import com.example.spark.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByEvent_Id(Long eventID);
    Task findByName(String name);
}

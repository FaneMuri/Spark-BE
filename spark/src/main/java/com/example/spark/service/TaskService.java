package com.example.spark.service;

import com.example.spark.controller.TaskRequest;
import com.example.spark.model.*;
import com.example.spark.model.DTO.TaskDTO;
import com.example.spark.repository.CommentTaskRepository;
import com.example.spark.repository.EventRepository;
import com.example.spark.repository.TaskRepository;
import com.example.spark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EventRepository eventRepository;
    private final CommentTaskRepository commentTaskRepository;
    private final UserRepository userTaskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, EventRepository eventRepository, CommentTaskRepository commentTaskRepository, UserRepository userTaskRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
        this.commentTaskRepository = commentTaskRepository;
        this.userTaskRepository = userTaskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> findAllTaskByEvent_Id(Long id) {
        return taskRepository.findAllByEvent_Id(id);
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(TaskDTO taskr) {
        Task task = new Task();
        task.setName(taskr.getName());
        task.setDescription(taskr.getDescription());
        task.setStatus(TaskStatus.TO_DO);
        LocalDateTime deadline = taskr.getDeadline();
        task.setDeadline(deadline);
        Event event = eventRepository.getReferenceById(taskr.getEventID());
        task.setEvent(event);
        List<User> users = new ArrayList<>();
        task.setUsers(users);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDTO updateStatus(TaskDTO taskDTO) {
        Task task = taskRepository.findByName(taskDTO.getName());
        task.setStatus(TaskStatus.valueOf( taskDTO.status));
        taskRepository.save(task);
        TaskDTO dto = new TaskDTO();
        dto.setName(taskDTO.getName());
        dto.setDescription(taskDTO.getDescription());
        dto.setDeadline(taskDTO.getDeadline());
        dto.setStatus(taskDTO.getStatus());
        return dto;
    }
}

package com.example.spark.service;

import com.example.spark.controller.TaskRequest;
import com.example.spark.model.*;
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

    public Task saveTask(TaskRequest taskr) {
        Task task = new Task();
        task.setName(taskr.getTaskName());
        task.setDescription(taskr.getTaskDescription());
        task.setStatus(TaskStatus.valueOf(taskr.getTaskStatus()));
        LocalDateTime deadline = taskr.getTaskDeadline().toLocalDateTime();
        task.setDeadline(deadline);
        Event event = eventRepository.getReferenceById(taskr.getEventID());
        task.setEvent(event);
        List<CommentTask> comments  = new ArrayList<>();
        for(var i : taskr.getCommentIDs())
        {
            comments.add(commentTaskRepository.getReferenceById(i));
        }
        task.setComments(comments);
        List<User> users = new ArrayList<>();
        for (var i : taskr.getUserIDs()) {
            User user = userTaskRepository.findById(i).orElseThrow(() -> new RuntimeException("User not found"));
            users.add(user);
            user.getTasks().add(task);
        }
        task.setUsers(users);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

package com.example.spark.controller;

import com.example.spark.model.DTO.TaskDTO;
import com.example.spark.model.Task;
import com.example.spark.model.TaskStatus;
import com.example.spark.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public List<TaskDTO> getAllTasksByEventId(@PathVariable Long id) {
        var x = taskService.findAllTaskByEvent_Id(id);
        List<TaskDTO> tasks = new ArrayList<>();
        for(var task : x){
            if(task.getStatus()== TaskStatus.TO_DO)
            {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setStatus("TO_DO");
                taskDTO.setName(task.getName());
                taskDTO.setDescription(task.getDescription());
                if(!task.getUsers().isEmpty())
                    taskDTO.setUser(task.getUsers().get(0));
                tasks.add(taskDTO);
            }
        };
        return tasks;
    }

    @PutMapping
    public TaskDTO updateStatus(@RequestBody TaskDTO taskDTO) {
        return taskService.updateStatus(taskDTO);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskDTO task) {return taskService.saveTask(task);}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}

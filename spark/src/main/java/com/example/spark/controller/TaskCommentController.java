package com.example.spark.controller;

import com.example.spark.model.Comment;
import com.example.spark.model.CommentTask;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.model.DTO.AddTaskCommentDTO;
import com.example.spark.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-comments")
public class TaskCommentController {
    private final CommentService commentService;

    public TaskCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentTask> getAllTaskComments() {
        return commentService.findAllCommentTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentTask> getCommentById(@PathVariable Long id) {
        return commentService.findCommentTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CommentTask createCommentTask(@RequestBody AddTaskCommentDTO commentDTO) {
        CommentTask commentTask = commentService.createTaskCommentFromDTO(commentDTO);
        return commentService.saveCommentTask(commentTask);
    }
}

package com.example.spark.controller;

import com.example.spark.model.Comment;
import com.example.spark.model.CommentTask;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.model.DTO.AddTaskCommentDTO;
import com.example.spark.service.CommentService;
import com.example.spark.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-comments")
public class TaskCommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    public TaskCommentController(CommentService commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
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
    public CommentTask createCommentTask(
            @RequestHeader("Authorization") String auth,
            @RequestBody AddTaskCommentDTO commentDTO
    ) {
        Long userId = Long.valueOf(jwtService.extractIdFromAuthorization(auth));
        commentDTO.setUserId(userId);
        CommentTask commentTask = commentService.createTaskCommentFromDTO(commentDTO);
        return commentService.saveCommentTask(commentTask);
    }
}

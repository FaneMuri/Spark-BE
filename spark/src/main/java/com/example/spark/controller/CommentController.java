package com.example.spark.controller;

import com.example.spark.model.Comment;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.model.Event;
import com.example.spark.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return commentService.findCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comment createComment(@RequestBody AddPostCommentDTO commentDTO) {
        Comment comment = commentService.createPostCommentFromDTO(commentDTO);
        return commentService.saveComment(comment);
    }
}

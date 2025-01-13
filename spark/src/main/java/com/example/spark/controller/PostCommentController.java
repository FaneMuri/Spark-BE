package com.example.spark.controller;

import com.example.spark.model.Comment;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-comments")
public class PostCommentController {
    private final CommentService commentService;

    public PostCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllCommentPosts() {
        return commentService.findAllCommentPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentPostById(@PathVariable Long id) {
        return commentService.findCommentPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comment createCommentPost(@RequestBody AddPostCommentDTO commentDTO) {
        Comment comment = commentService.createPostCommentFromDTO(commentDTO);
        return commentService.saveCommentPost(comment);
    }
}

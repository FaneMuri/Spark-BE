package com.example.spark.controller;

import com.example.spark.model.Comment;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.service.CommentService;
import com.example.spark.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-comments")
public class PostCommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    public PostCommentController(CommentService commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
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
    public Comment createCommentPost(
            @RequestHeader("Authorization") String auth,
            @RequestBody AddPostCommentDTO commentDTO
    ) {
        Long userId = Long.valueOf(jwtService.extractIdFromAuthorization(auth));
        commentDTO.setUserId(userId);
        Comment comment = commentService.createPostCommentFromDTO(commentDTO);
        return commentService.saveCommentPost(comment);
    }
}

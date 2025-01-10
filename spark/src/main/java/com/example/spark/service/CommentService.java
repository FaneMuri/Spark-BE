package com.example.spark.service;

import com.example.spark.model.Comment;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.model.Post;
import com.example.spark.model.User;
import com.example.spark.repository.CommentRepository;
import com.example.spark.repository.PostRepository;
import com.example.spark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findCommentById(long id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment createPostCommentFromDTO(AddPostCommentDTO commentDTO) {
        Comment comment = new Comment();

        Optional<Post> post = postRepository.findById(commentDTO.getPostId());
        comment.setPost(post.get());

        Optional<User> user = userRepository.findById(commentDTO.getUserId());
        comment.setUser(user.get());

        comment.setMessage(commentDTO.getMessage());

        return comment;
    }
}

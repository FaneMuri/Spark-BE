package com.example.spark.service;

import com.example.spark.model.*;
import com.example.spark.model.DTO.AddPostCommentDTO;
import com.example.spark.model.DTO.AddTaskCommentDTO;
import com.example.spark.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentPostRepository;
    private final CommentTaskRepository commentTaskRepository;
    private final PostRepository postRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(
            CommentRepository commentPostRepository,
            CommentTaskRepository commentTaskRepository,
            PostRepository postRepository,
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.commentTaskRepository = commentTaskRepository;
        this.commentPostRepository = commentPostRepository;
        this.postRepository = postRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> findAllCommentPosts() {
        return commentPostRepository.findAll();
    }

    public Optional<Comment> findCommentPostById(long id) {
        return commentPostRepository.findById(id);
    }

    public Comment saveCommentPost(Comment comment) {
        return commentPostRepository.save(comment);
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

    public List<CommentTask> findAllCommentTasks() {
        return commentTaskRepository.findAll();
    }

    public Optional<CommentTask> findCommentTaskById(long id) {
        return commentTaskRepository.findById(id);
    }

    public CommentTask saveCommentTask(CommentTask comment) {
        return commentTaskRepository.save(comment);
    }

    public CommentTask createTaskCommentFromDTO(AddTaskCommentDTO commentDTO) {
        CommentTask comment = new CommentTask();

        Optional<Task> task = taskRepository.findById(commentDTO.getTaskId());
        comment.setTask(task.get());

        Optional<User> user = userRepository.findById(commentDTO.getUserId());
        comment.setUser(user.get());

        comment.setMessage(commentDTO.getMessage());

        return comment;
    }
}

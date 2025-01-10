package com.example.spark.service;

import com.example.spark.model.DTO.AddPostDTO;
import com.example.spark.model.Event;
import com.example.spark.model.Post;
import com.example.spark.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final EventService eventService;

    @Autowired
    public PostService(PostRepository postRepository, EventService eventService) {
        this.postRepository = postRepository;
        this.eventService = eventService;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post createPostFromDTO(AddPostDTO addPostDTO) {
        Post post = new Post();

        Optional<Event> event = eventService.findEventById(addPostDTO.getEventId());
        post.setEvent(event.get());

        post.setName(addPostDTO.getName());
        post.setDescription(addPostDTO.getDescription());
        post.setComments(new ArrayList<>());
        post.setImage(Base64.getMimeDecoder().decode(addPostDTO.getImage()));
        return post;
    }
}

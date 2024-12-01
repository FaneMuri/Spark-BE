package com.example.spark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    // Relationship with User
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore
    private User user;

    // Relationship with Post
    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    @JsonIgnore
    private Post post;
}


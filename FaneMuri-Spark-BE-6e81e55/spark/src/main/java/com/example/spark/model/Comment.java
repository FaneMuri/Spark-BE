package com.example.spark.model;

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
    private User user;

    // Relationship with Post
    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;
}


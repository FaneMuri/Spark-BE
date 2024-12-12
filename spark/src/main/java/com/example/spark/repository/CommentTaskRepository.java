package com.example.spark.repository;

import com.example.spark.model.CommentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTaskRepository extends JpaRepository<CommentTask,Long> {
}


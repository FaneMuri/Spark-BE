package com.example.spark.repository;

import com.example.spark.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// find all and save is already implemented
// if you want to make a find by just write Optional<Event> findByAttribute(Attribute) ; will be generate the query
@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
}

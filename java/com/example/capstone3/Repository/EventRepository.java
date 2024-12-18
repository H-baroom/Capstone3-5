package com.example.capstone3.Repository;

import com.example.capstone3.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findEventById(Integer id);
}

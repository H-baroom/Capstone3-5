package com.example.capstone3.Repository;

import com.example.capstone3.Model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    EventRegistration findEventRegistrationById(Integer id);
}

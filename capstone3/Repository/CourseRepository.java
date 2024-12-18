package com.example.capstone3.Repository;

import com.example.capstone3.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(Integer id);
}

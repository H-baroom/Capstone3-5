package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Course;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.OutDTO.CourseOutDTO;
import com.example.capstone3.Repository.CourseRepository;
import com.example.capstone3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final OwnerRepository ownerRepository;


    public List<CourseOutDTO> getAllCourses(){

        List<Course> courses = courseRepository.findAll();

        List<CourseOutDTO> courseOutDTOS = new ArrayList<>();

        for(Course course : courses){
            CourseOutDTO courseOutDTO = new CourseOutDTO(course.getName(), course.getDescription(), course.getPrice(), course.getDuration());

            courseOutDTOS.add(courseOutDTO);
        }
        return courseOutDTOS;
    }

    public void addCourse(Integer owner_id, Course course) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        //assign course to one owner
        course.setOwner(owner);
        courseRepository.save(course);
    }


    public void updateCourse(Integer id, Course course) {

        Course c = courseRepository.findCourseById(id);
        if (c == null)
            throw new ApiException("Course not found!");

        c.setName(course.getName());
        c.setDescription(c.getDescription());
        c.setPrice(course.getPrice());
        c.setDuration(course.getDuration());
        courseRepository.save(c);
    }

    public void deleteCourse(Integer id){

        Course course = courseRepository.findCourseById(id);
        if(course == null)
            throw new ApiException("Course not found!");

        courseRepository.delete(course);

    }








}
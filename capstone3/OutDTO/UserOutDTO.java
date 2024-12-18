package com.example.capstone3.OutDTO;

import com.example.capstone3.Model.BookingCourse;
import com.example.capstone3.Model.EventRegistration;
import com.example.capstone3.Model.Purchase;
import com.example.capstone3.Model.RentingRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserOutDTO {

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private String address;


    private List<EventRegistrationOutDTO> eventRegistrations;

    private List<PurchaseOutDTO> purchases;

    private List<RentingRequestOutDTO> rentingRequests;

    private List<BookingCourseOutDTO> bookingCourses;

}

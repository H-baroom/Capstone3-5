package com.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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

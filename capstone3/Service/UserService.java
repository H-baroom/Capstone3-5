package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.*;
import com.example.capstone3.OutDTO.*;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;

    public List<UserOutDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserOutDTO> userOutDTOs = new ArrayList<>();
        List<EventRegistration> eventRegistrations = new ArrayList<>();
        List<EventRegistrationOutDTO> eventRegistrationOutDTOs = new ArrayList<>();
        List<Purchase> purchases = new ArrayList<>();
        List<PurchaseOutDTO> purchaseOutDTOs = new ArrayList<>();
        List<RentingRequest> rentingRequests = new ArrayList<>();
        List<RentingRequestOutDTO> rentingRequestOutDTOs = new ArrayList<>();
        List<BookingCourse> bookingCourses = new ArrayList<>();
        List<BookingCourseOutDTO> bookingCourseOutDTOs = new ArrayList<>();
        for (User user : users) {
            for (EventRegistration er:user.getEventRegistrations()){
                eventRegistrations.add(er);
                eventRegistrationOutDTOs.add(new EventRegistrationOutDTO(er.getEvent().getId(),er.getOwner().getId(),er.getUser().getId()));
            }
            for (Purchase p:purchases){
                purchases.add(p);
                Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(p.getMotorcycle_id());
                purchaseOutDTOs.add(new PurchaseOutDTO(p.getPurchaseDate(),new PurchaseUserOutDTO(user.getName(),user.getEmail(),user.getPhoneNumber(),user.getAge(),user.getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
            }
            for (RentingRequest r:rentingRequests){
                rentingRequests.add(r);
                rentingRequestOutDTOs.add(new RentingRequestOutDTO(r.getRequestDate(),r.getStartDate(),r.getEndDate(),r.getTotalCost()));
            }
            for (BookingCourse bc:bookingCourses){
                bookingCourses.add(bc);
                bookingCourseOutDTOs.add(new BookingCourseOutDTO(bc.getBookingDate(),bc.getCourseStartDate(),bc.getCourseEndDate()));
            }
            userOutDTOs.add(new UserOutDTO(user.getName(),user.getEmail(),user.getPhoneNumber(),user.getAge(),user.getAddress(),eventRegistrationOutDTOs,purchaseOutDTOs,rentingRequestOutDTOs,bookingCourseOutDTOs));
        }
        return userOutDTOs;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id,User user) {
        User user1 = userRepository.findUserById(id);
        if (user1 == null){
            throw new ApiException("User not found");
        }
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAge(user.getAge());
        user1.setAddress(user.getAddress());
        userRepository.save(user1);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }


}
package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.InDTO.RentingRequestInDTO;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Renting;
import com.example.capstone3.Model.RentingRequest;
import com.example.capstone3.Model.User;
import com.example.capstone3.OutDTO.RentingOutDTO;
import com.example.capstone3.OutDTO.RentingRequestOutDTO;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.RentingRepository;
import com.example.capstone3.Repository.RentingRequestRepository;
import com.example.capstone3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.IClassFileProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentingRequestService {

    private final RentingRequestRepository rentingRequestRepository;
    private final RentingRepository rentingRepository;
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;


    public List<RentingRequestOutDTO> getAllRentingRequests() {
        List<RentingRequest> rentingRequests = rentingRequestRepository.findAll();
        List<RentingRequestOutDTO> rentingRequestOutDTOs = new ArrayList<>();
        for (RentingRequest rentingRequest : rentingRequests) {
            rentingRequestOutDTOs.add(new RentingRequestOutDTO(rentingRequest.getRequestDate(),rentingRequest.getStartDate(),rentingRequest.getEndDate(),rentingRequest.getTotalCost()));
        }
        return rentingRequestOutDTOs;
    }


    public Integer addRentingRequest(RentingRequestInDTO rentingRequestDTOIn) {
        // Step 1: Validate input dates
        if (rentingRequestDTOIn.getStartDate().isAfter(rentingRequestDTOIn.getEndDate())) {
            throw new ApiException("Start date cannot be after end date!");
        }
        if (rentingRequestDTOIn.getStartDate().isBefore(LocalDate.now())) {
            throw new ApiException("Start date must be today or in the future!");
        }

        // Step 2: Check if the Renting offer exists
        Renting renting = rentingRepository.findRentingById(rentingRequestDTOIn.getRenting_id());
        if(renting ==null){
            throw  new ApiException("Renting offer not found!");}

        // Step 3: Check for existing rentals on the selected motorcycle
        boolean isRented = rentingRepository.existsByMotorcycleAndDateRange(
                renting.getMotorcycle_id(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate()
        );
        if (isRented) {
            throw new ApiException("The motorcycle is not available for the requested dates!");
        }

        // Step 4: Fetch the User
        User user = userRepository.findUserById(rentingRequestDTOIn.getUser_id());
        if(user ==null){
            throw  new ApiException("User not found!");}
        // Step 5: Create and save RentingRequest
        RentingRequest rentingRequest = new RentingRequest();
        rentingRequest.setUser(user);
        rentingRequest.setRenting(renting);
        rentingRequest.setStartDate(rentingRequestDTOIn.getStartDate());
        rentingRequest.setEndDate(rentingRequestDTOIn.getEndDate());
        rentingRequest.setMotorcycle_id(rentingRequestDTOIn.getMotorcycle_id());

        // Calculate total cost based on price per day
        int totalCost = calculateTotalCost(renting.getPricePerDay(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate());
        rentingRequest.setTotalCost(totalCost);

        rentingRequestRepository.save(rentingRequest);

        // Return success message
        return totalCost;
    }

    // Helper method to calculate total cost
    private Integer calculateTotalCost(Double pricePerDay, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return (int) (days * pricePerDay);
    }

//
//    public void updateRentingRequest(Integer rentingRequest_id,RentingRequestDTO_In rentingRequestInDTO) {
//        Renting renting = rentingRepository.findRentingById(rentingRequestInDTO.getRenting_id());
//        if(renting == null) {
//            throw new ApiException("Renting not found");
//        }
//        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id);
//        if(rentingRequest == null) {
//            throw new ApiException("Renting Request not found");
//        }
//        User user = userRepository.findUserById(rentingRequestInDTO.getUser_id());
//        if(user == null) {
//            throw new ApiException("User not found");
//        }
//        rentingRequest.setStartDate(rentingRequestInDTO.getStartDate());
//        rentingRequest.setEndDate(rentingRequestInDTO.getEndDate());
//        rentingRequest.setRenting(renting);
//        rentingRequest.setUser(user);
//        rentingRequest.setMotorcycleId(rentingRequestInDTO.getMotorcycleId());
//        rentingRequestRepository.save(rentingRequest);
//    }


    public void updateRentingRequest(Integer rentingRequestId, RentingRequestInDTO rentingRequestInDTO) {
        // Step 1: Validate the RentingRequest exists
        RentingRequest existingRentingRequest = rentingRequestRepository.findById(rentingRequestId)
                .orElseThrow(() -> new ApiException("Renting Request not found"));

        // Step 2: Validate the Renting offer exists
        Renting renting = rentingRepository.findById(rentingRequestInDTO.getRenting_id())
                .orElseThrow(() -> new ApiException("Renting not found"));

        // Step 3: Validate the User exists
        User user = userRepository.findById(rentingRequestInDTO.getUser_id())
                .orElseThrow(() -> new ApiException("User not found"));

        // Step 4: Validate input dates
        if (rentingRequestInDTO.getStartDate().isAfter(rentingRequestInDTO.getEndDate())) {
            throw new ApiException("Start date cannot be after end date!");
        }
        if (rentingRequestInDTO.getStartDate().isBefore(LocalDate.now())) {
            throw new ApiException("Start date must be today or in the future!");
        }

        // Step 5: Check motorcycle availability (excluding current renting request)
        boolean isRented = rentingRepository.existsByMotorcycleAndDateRangeExcludingRequest(
                renting.getMotorcycle_id(),
                rentingRequestInDTO.getStartDate(),
                rentingRequestInDTO.getEndDate(),
                rentingRequestId
        );
        if (isRented) {
            throw new ApiException("The motorcycle is not available for the updated dates!");
        }

        // Step 6: Update the RentingRequest
        existingRentingRequest.setStartDate(rentingRequestInDTO.getStartDate());
        existingRentingRequest.setEndDate(rentingRequestInDTO.getEndDate());
        existingRentingRequest.setRenting(renting);
        existingRentingRequest.setUser(user);
        existingRentingRequest.setMotorcycle_id(rentingRequestInDTO.getMotorcycle_id());

        // Step 7: Recalculate and update total cost
        int totalCost = calculateTotalCost(renting.getPricePerDay(), rentingRequestInDTO.getStartDate(), rentingRequestInDTO.getEndDate());
        existingRentingRequest.setTotalCost(totalCost);

        // Save the updated RentingRequest
        rentingRequestRepository.save(existingRentingRequest);
    }



    public void deleteRentingRequest(Integer rentingRequest_id) {
        // Step 1: Find the RentingRequest
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id) ;
        if(rentingRequest==null)
        {new ApiException("Renting request not found!");}

        // Step 2: Calculate hours until start date
        LocalDateTime startDateTime = rentingRequest.getStartDate().atStartOfDay();
        long hoursUntilStart = Duration.between(LocalDateTime.now(), startDateTime).toHours();

        // Step 3: Check if cancellation is allowed
        if (hoursUntilStart <= 48) {
            throw new ApiException("Cannot cancel the request as the start date is less than 48 hours away!");
        }

        // Step 4: Nullify any associated Renting (if needed)
        Renting renting = rentingRequest.getRenting();
        if (renting != null) {
            renting.setRentingRequests(null);
            rentingRepository.save(renting);
        }

        // Step 5: Delete the RentingRequest
        rentingRequestRepository.delete(rentingRequest);
    }







    public void extendRental(Integer rentingRequestId, LocalDate newEndDate) {
        // Step 1: Validate the Renting Request exists
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequestId) ;
        if (rentingRequest == null) {
        new ApiException("Renting Request not found");
        }

        // Step 2: Check that the new end date is after the current end date
        if (!newEndDate.isBefore(rentingRequest.getEndDate()) || newEndDate.isEqual(rentingRequest.getEndDate())) {
            throw new ApiException("New end date must be after the current end date!");
        }

        // Step 3: Check motorcycle availability for the new period
        boolean isRented = rentingRepository.existsByMotorcycleAndDateRange(
                rentingRequest.getRenting().getMotorcycle_id(),
                rentingRequest.getStartDate(), // Start checking from the day after the current end date
                newEndDate
        );
        if (isRented) {
            throw new ApiException("The motorcycle is not available for the requested extension period!");
        }

        // Step 4: Update the end date
        rentingRequest.setEndDate(newEndDate);

        // Step 5: Recalculate the total cost
        int updatedTotalCost = calculateTotalCost(
                rentingRequest.getRenting().getPricePerDay(),
                rentingRequest.getStartDate(),
                newEndDate
        );
        rentingRequest.setTotalCost(updatedTotalCost);

        // Step 6: Save the updated RentingRequest
        rentingRequestRepository.save(rentingRequest);
    }


}

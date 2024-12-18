package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.InDTO.MaintenanceRequestInDTO;
import com.example.capstone3.Model.MaintenanceExpert;
import com.example.capstone3.Model.MaintenanceRequest;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.OutDTO.MaintenanceRequestOutDTO;
import com.example.capstone3.Repository.MaintenanceExpertRepository;
import com.example.capstone3.Repository.MaintenanceRequestRepository;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MaintenanceExpertRepository maintenanceExpertRepository;



    public List<MaintenanceRequestOutDTO> getAllMaintenanceRequest(){

        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        List<MaintenanceRequestOutDTO> maintenanceRequestOutDTOS = new ArrayList<>();

        for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
            MaintenanceRequestOutDTO motorcycleOutDTOS = new MaintenanceRequestOutDTO(maintenanceRequest.getExpertName(), maintenanceRequest.getRequestDate(), maintenanceRequest.getTotalPrice(), maintenanceRequest.getStatus(), maintenanceRequest.getPickupDate() );
            maintenanceRequestOutDTOS.add(motorcycleOutDTOS);
        }
        return maintenanceRequestOutDTOS;
    }

    public void addMaintenanceRequest(MaintenanceRequestInDTO maintenanceRequestInDTO){


        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequestInDTO.getMotorcycle_id());
        if(motorcycle == null){
            throw new ApiException("Motorcycle not found");
        }

        Owner owner = ownerRepository.findOwnerById(maintenanceRequestInDTO.getOwner_id());
        if(owner == null)
            throw new ApiException("Owner not found");


        if (maintenanceRequestInDTO.getPickupDate().isBefore(LocalDate.now())) {
            throw new ApiException("Pickup date cannot be in the past!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestInDTO.getNameOfExpert());
        if (expert == null) {
            throw new ApiException("Expert not found!");
        }

        Double totalPrice = calculateTotalPrice(expert, maintenanceRequestInDTO.getPickupDate());

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest( maintenanceRequestInDTO.getNameOfExpert(), motorcycle, owner, maintenanceRequestInDTO.getPickupDate());

        maintenanceRequest.setStatus("Pending");
        maintenanceRequest.setTotalPrice(totalPrice);

        maintenanceRequestRepository.save(maintenanceRequest);

    }

    //method to calculate total price
    private Double calculateTotalPrice(MaintenanceExpert expert, LocalDate pickupDate) {
        // calc price based on expert daily rate and number of days
        Double numberOfDays = (double) Duration.between(LocalDate.now().atStartOfDay(), pickupDate.atStartOfDay()).toDays();

        // Calculate total price as the daily rate times the number of days
        return expert.getMaintenancePricePerDay() * numberOfDays;
    }


    public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequestInDTO maintenanceRequestInDTO){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest ==null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequest.setExpertName(maintenanceRequestInDTO.getNameOfExpert());
        maintenanceRequest.setPickupDate(maintenanceRequestInDTO.getPickupDate());


        if (maintenanceRequestInDTO.getOwner_id() != null) {
            Owner owner = ownerRepository.findOwnerById(maintenanceRequestInDTO.getOwner_id());
            if(owner== null)
                throw new ApiException("Owner not found !");

            maintenanceRequest.setOwner(owner);
        }

        if (maintenanceRequestInDTO.getMotorcycle_id() != null) {
            maintenanceRequest.setMotorcycle_id(maintenanceRequestInDTO.getMotorcycle_id());
        }

        if (maintenanceRequestInDTO.getPickupDate() != null) {
            MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestInDTO.getNameOfExpert());
            if (expert == null) {
                throw new ApiException("Expert not found");
            }

            // calculate again the total price if the pickupdate is changed
            Double newTotalPrice = calculateTotalPrice(expert, maintenanceRequestInDTO.getPickupDate());
            maintenanceRequest.setTotalPrice(newTotalPrice);
        }

        maintenanceRequestRepository.save(maintenanceRequest);
    }


    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the pickupDate is after the current date (meaning the expert has completed their work)
        if (maintenanceRequest.getPickupDate() != null && maintenanceRequest.getPickupDate().isAfter(LocalDate.now())) {
            throw new ApiException("Cannot delete this Maintenance Request !");
        }

        maintenanceRequestRepository.delete(maintenanceRequest);

    }

    public void updateMaintenanceRequestStatusToCompleted(Integer maintenanceRequest_id, String expertName) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if (maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the current expert is the one assigned to the request
        if (!maintenanceRequest.getExpertName().equalsIgnoreCase(expertName)) {
            throw new ApiException("Only the expert can mark the maintenance request as completed!");
        }

        // Only allow the status to be updated if the request is in 'Pending' status
        if (!"Pending".equalsIgnoreCase(maintenanceRequest.getStatus())) {
            throw new ApiException("Maintenance request is not in a Pending status, it cannot be marked as completed!");
        }

        // Update status
        maintenanceRequest.setStatus("Completed");
        maintenanceRequestRepository.save(maintenanceRequest);
    }




















}
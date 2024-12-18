package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.Model.Renting;
import com.example.capstone3.OutDTO.MotorcycleOutDTO;
import com.example.capstone3.OutDTO.RentingDTO;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.OwnerRepository;
import com.example.capstone3.Repository.RentingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentingService {
    private final RentingRepository rentingRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;

    public List<RentingDTO> getAllRentings(){

        List<Renting> rentings = rentingRepository.findAll();

        List<RentingDTO> rentingDTOS = new ArrayList<>();
        List<MotorcycleOutDTO> motorcycles = motorcycleService.getAllMotorcycles();


        for(Renting renting : rentings){
            RentingDTO rentingDTO = new RentingDTO(renting.getPricePerDay(), renting.getPickupLocation(), renting.getDropOffLocation());

            rentingDTOS.add(rentingDTO);
        }
        return rentingDTOS;
    }

    public void addRenting(Integer owner_id, Renting renting) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");


        renting.setOwner(owner);
        rentingRepository.save(renting);
    }


    public void updateRenting(Integer id, Renting renting) {

        Renting r = rentingRepository.findRentingById(id);

        if (r == null)
            throw new ApiException("Renting not found!");

        r.setPricePerDay(renting.getPricePerDay());
        r.setPickupLocation(renting.getPickupLocation());
        r.setDropOffLocation(renting.getDropOffLocation());
        rentingRepository.save(r);
    }

    public void deleteRenting(Integer id){

        Renting renting = rentingRepository.findRentingById(id);
        if(renting == null)
            throw new ApiException("Renting not found!");

        rentingRepository.delete(renting);

    }

}
package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.OutDTO.MotorcycleOutDTO;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final OwnerRepository ownerRepository;

    public List<MotorcycleOutDTO> getAllMotorcycles(){

        List<Motorcycle> motorcycles = motorcycleRepository.findAll();

        List<MotorcycleOutDTO> motorcycleOutDTOS = new ArrayList<>();

        for(Motorcycle motorcycle : motorcycles){

            MotorcycleOutDTO motorcycleOutDTO = new MotorcycleOutDTO(motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.getPrice(), motorcycle.getColor(), motorcycle.getIsAvailable(),motorcycle.getIsForSale(),motorcycle.getHasOffer());

            motorcycleOutDTOS.add(motorcycleOutDTO);
        }

        return motorcycleOutDTOS;
    }

    public void addMotorcycle(Integer owner_id, Motorcycle motorcycle) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        //assign motorcycle to one owner
        motorcycle.setOwner(owner);
        motorcycleRepository.save(motorcycle);
    }


    public void updateMotorcycle(Integer id, Motorcycle motorcycle) {

        Motorcycle m = motorcycleRepository.findMotorcycleById(id);
        if (m == null)
            throw new ApiException("Motorcycle not found!");

        m.setBrand(motorcycle.getBrand());
        m.setModel(motorcycle.getModel());
        m.setPrice(motorcycle.getPrice());
        m.setColor(motorcycle.getColor());
        m.setYear(motorcycle.getYear());
        m.setIsAvailable(motorcycle.getIsAvailable());
        m.setIsForSale(motorcycle.getIsForSale());
        motorcycleRepository.save(m);
    }

    public void deleteMotorcycle(Integer id){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if(motorcycle == null)
            throw new ApiException("Motorcycle not found!");

        motorcycleRepository.delete(motorcycle);

    }

    public List<Motorcycle> getAvailableMotorcycles(){
        List<Motorcycle> availableMotorcycles = motorcycleRepository.findMotorcycleByIsAvailable(true);
        return availableMotorcycles;
    }

    public List<Motorcycle> getMotorcyclesForSale(){
        List<Motorcycle> MotorcyclesForSale = motorcycleRepository.findMotorcycleByIsForSale(true);
        return MotorcyclesForSale;
    }

    public void changeForSaleStatus(Integer id,Boolean forSale,Double price){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }
        motorcycle.setPrice(price);
        motorcycle.setIsForSale(forSale);
        motorcycleRepository.save(motorcycle);
    }
    public void changeAvailableStatus(Integer id,Boolean Available){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }
        motorcycle.setIsAvailable(Available);
        motorcycleRepository.save(motorcycle);
    }




    public void discountMotorcycle(Integer motorcycle_id, Integer owner_id,Double discountRate){
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(motorcycle_id);
        if (motorcycle == null){
            throw new ApiException("Motorcycle not found!");
        }
        Owner owner = ownerRepository.findOwnerById(owner_id);
        Owner ownerMotorcycle = ownerRepository.findOwnerByMotorcyclesId(motorcycle.getId());
        if (owner == null || ownerMotorcycle == null){
            throw new ApiException("Owner not found!");
        }
        ArrayList<Double> allowedDiscounts = new ArrayList<>();
        allowedDiscounts.add(20.0);
        allowedDiscounts.add(35.0);
        allowedDiscounts.add(50.0);
        if (motorcycle.getIsForSale()){
            if (motorcycle.getIsAvailable()){
                if(allowedDiscounts.contains(discountRate)){
                    double newPrice = motorcycle.getPrice() * (1 - discountRate / 100);
                    motorcycle.setPrice(newPrice);
                    motorcycle.setHasOffer(true);
                    motorcycleRepository.save(motorcycle);
                }else {
                    throw new ApiException("Not valid discount rate!");
                }
            }else {
                throw new ApiException("Motorcycle not Available!");
            }
        }else {
            throw new ApiException("Motorcycle not ForSale!");
        }

    }

    public Double CalculateAveragePriceForSameBrandAndYear(String brand,Integer year){
        List<Motorcycle> SameBrandAndYear = motorcycleRepository.findMotorcycleByBrandAndYear(brand,year);
        List<Motorcycle> forSale = new ArrayList<>();

        Double price =0.0;
        for (Motorcycle motorcycle : SameBrandAndYear){
            if (motorcycle.getIsForSale()){
                forSale.add(motorcycle);
                price += motorcycle.getPrice();
            }
        }
        return price/forSale.size();
    }









}
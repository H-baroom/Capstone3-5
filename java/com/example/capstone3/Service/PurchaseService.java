package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.InDTO.PurchaseInDTO;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.Model.Purchase;
import com.example.capstone3.Model.User;
import com.example.capstone3.OutDTO.PurchaseMotorcycleOutDTO;
import com.example.capstone3.OutDTO.PurchaseOutDTO;
import com.example.capstone3.OutDTO.PurchaseUserOutDTO;
import com.example.capstone3.Repository.MotorcycleRepository;
import com.example.capstone3.Repository.OwnerRepository;
import com.example.capstone3.Repository.PurchaseRepository;
import com.example.capstone3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    public List<PurchaseOutDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseOutDTO> purchaseOutDTOs = new ArrayList<>();
        for (Purchase purchase : purchases) {
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchase.getMotorcycle_id());
            purchaseOutDTOs.add(new PurchaseOutDTO(purchase.getPurchaseDate(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
        }
        return purchaseOutDTOs;
    }


    public void addPurchase(PurchaseInDTO purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycle_id());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUser_id());
        if (user == null) {
            throw new ApiException("User not found");
        }
        Owner owner = ownerRepository.findOwnerById(purchaseInDTO.getOwner_id());
        Set<Motorcycle> motorcycleOwner = owner.getMotorcycles();
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        for (Motorcycle mo:owner.getMotorcycles()){
            if (mo.getId() == motorcycle.getId()) {
                if (motorcycle.getIsAvailable() && motorcycle.getIsForSale()) {
                    motorcycleOwner.remove(mo);
                    Purchase purchase = new Purchase(user, motorcycle.getId());
                    owner.setMotorcycles(motorcycleOwner);
                    ownerRepository.save(owner);
                    purchaseRepository.save(purchase);
                }
            }
        }

    }



}

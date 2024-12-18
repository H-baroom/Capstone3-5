package com.example.capstone3.OutDTO;

import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class PurchaseOutDTO {
    private LocalDate purchaseDate ;

    private PurchaseUserOutDTO user;

    private PurchaseMotorcycleOutDTO motorcycle;
}

package com.example.capstone3.OutDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PurchaseMotorcycleOutDTO {
    private String brand; // e.g., Yamaha, Honda

    private String model;

    private Integer year;

    private Double price;

    private String color;

    private Boolean isAvailable;
}

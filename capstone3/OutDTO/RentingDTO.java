package com.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RentingDTO {
    private Double pricePerDay;

    private String pickupLocation;

    private String dropOffLocation;

}

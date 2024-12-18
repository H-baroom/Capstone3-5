package com.example.capstone3.OutDTO;

import com.example.capstone3.Model.RentingRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RentingOutDTO {

    private Double pricePerDay;

    private String pickupLocation;

    private String dropOffLocation;

    private RentingRequestOutDTO rentingRequestOutDTO;
}

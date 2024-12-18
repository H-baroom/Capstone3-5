package com.example.capstone3.OutDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class MaintenanceRequestOutDTO {

    private LocalDate requestDate ;

    private Double totalPrice;

    private String expertName;

    private String status;

    private LocalDate pickupDate;


    public MaintenanceRequestOutDTO(String expertName, LocalDate requestDate, Double totalPrice, @Pattern(regexp = "^(Pending|Completed)$") @NotEmpty(message = "varchar(10)") String status, LocalDate pickupDate) {
    }
}

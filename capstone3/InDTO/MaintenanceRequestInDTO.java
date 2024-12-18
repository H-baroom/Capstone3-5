package com.example.capstone3.InDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MaintenanceRequestInDTO {
    private Integer motorcycle_id;

    private Integer owner_id;

    private String nameOfExpert;

    private LocalDate pickupDate;
}

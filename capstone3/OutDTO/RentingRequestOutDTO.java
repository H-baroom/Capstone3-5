package com.example.capstone3.OutDTO;

import com.example.capstone3.Model.Renting;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class RentingRequestOutDTO {
    private LocalDate requestDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalCost;

}

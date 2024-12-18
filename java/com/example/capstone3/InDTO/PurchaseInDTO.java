package com.example.capstone3.InDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PurchaseInDTO {
    private Integer motorcycle_id;

    private Integer user_id;

    private Integer owner_id;
}

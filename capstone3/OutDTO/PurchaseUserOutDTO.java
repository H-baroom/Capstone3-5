package com.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PurchaseUserOutDTO {
    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private String address;
}
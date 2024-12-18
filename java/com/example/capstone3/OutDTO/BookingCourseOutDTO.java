package com.example.capstone3.OutDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class BookingCourseOutDTO {

    private LocalDate bookingDate;

    private LocalDate courseStartDate;

    private LocalDate courseEndDate;
}

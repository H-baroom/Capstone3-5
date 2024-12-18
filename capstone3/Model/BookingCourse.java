package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "date not null")
    @NotNull(message = "Booking date cannot be null")
    private LocalDate bookingDate = LocalDate.now();

    @Column(columnDefinition = "date not null")
    @FutureOrPresent(message = "Course start date must be in the present or future")
    private LocalDate courseStartDate;

    @Column(columnDefinition = "date not null")
    @Future(message = "Course end date must be in the future")
    private LocalDate courseEndDate;



    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Course course;
}

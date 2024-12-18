package com.example.capstone3.OutDTO;

import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRegistrationOutDTO {

    private Integer event_id;

    private Integer owner_id;

    private Integer user_id;

    private Integer motorcycle_id;

    private String status;
}

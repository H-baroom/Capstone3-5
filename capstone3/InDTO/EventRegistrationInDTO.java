package com.example.capstone3.InDTO;

import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventRegistrationInDTO {

    private Integer event_id;

    private Integer owner_id;

    private Integer user_id;

    private Integer motorcycle_id;

}

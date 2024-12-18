package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.InDTO.EventRegistrationInDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.OutDTO.EventRegistrationOutDTO;
import com.example.capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRegistrationService {
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;

    public List<EventRegistrationOutDTO> getEventRegistrations() {
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAll();
        List<EventRegistrationOutDTO> eventRegistrationOutDTOs = new ArrayList<>();
        for (EventRegistration eventRegistration : eventRegistrations) {
            eventRegistrationOutDTOs.add(new EventRegistrationOutDTO(eventRegistration.getEvent().getId(),eventRegistration.getOwner().getId(), eventRegistration.getUser().getId(), eventRegistration.getMotorcycle().getId(),eventRegistration.getStatus()));
        }
        return eventRegistrationOutDTOs;
    }
    public void addEventRegistration(EventRegistrationInDTO eventRegistrationInDTO) {
        Event event = eventRepository.findEventById(eventRegistrationInDTO.getEvent_id());
        Owner owner = ownerRepository.findOwnerById(eventRegistrationInDTO.getOwner_id());
        User user = userRepository.findUserById(eventRegistrationInDTO.getUser_id());
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(eventRegistrationInDTO.getMotorcycle_id());
        EventRegistration eventRegistration = new EventRegistration(event,owner,user,motorcycle);
        if (owner == null && motorcycle == null) {
            eventRegistration.setStatus("visitor");
            eventRegistrationRepository.save(eventRegistration);
        }else if (user == null) {
            eventRegistration.setStatus("owner");
            eventRegistrationRepository.save(eventRegistration);
        }else{
            throw new ApiException("not valid");
        }

    }

    public void updateEventRegistration(Integer id,EventRegistrationInDTO eventRegistrationInDTO) {
        EventRegistration eventRegistration1 = eventRegistrationRepository.findEventRegistrationById(id);
        if (eventRegistration1 == null) {
            throw new ApiException("Event Registration Not Found");
        }
        Event event = eventRepository.findEventById(eventRegistrationInDTO.getEvent_id());
        Owner owner = ownerRepository.findOwnerById(eventRegistrationInDTO.getOwner_id());
        User user = userRepository.findUserById(eventRegistrationInDTO.getUser_id());
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(eventRegistrationInDTO.getMotorcycle_id());

        if (owner == null && motorcycle == null) {
            eventRegistration1.setEvent(event);
            eventRegistration1.setOwner(owner);
            eventRegistration1.setUser(user);
            eventRegistration1.setMotorcycle(motorcycle);
            eventRegistration1.setStatus("visitor");
            eventRegistrationRepository.save(eventRegistration1);
        }else if (user == null) {
            eventRegistration1.setEvent(event);
            eventRegistration1.setOwner(owner);
            eventRegistration1.setUser(user);
            eventRegistration1.setMotorcycle(motorcycle);
            eventRegistration1.setStatus("owner");
            eventRegistrationRepository.save(eventRegistration1);
        }else{
            throw new ApiException("not valid");
        }
    }

    public void deleteEventRegistration(Integer id) {
        EventRegistration eventRegistration = eventRegistrationRepository.findEventRegistrationById(id);
        if (eventRegistration == null) {
            throw new ApiException("Event Registration Not Found");
        }
        eventRegistrationRepository.delete(eventRegistration);
    }
}

package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Owner;
import com.example.capstone3.OutDTO.CourseOutDTO;
import com.example.capstone3.OutDTO.MotorcycleOutDTO;
import com.example.capstone3.OutDTO.OwnerOutDTO;
import com.example.capstone3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final CourseService courseService;


    public List<OwnerOutDTO> getAllOwners(){

        List<Owner> owners = ownerRepository.findAll();

        List<OwnerOutDTO> ownerOutDTOList = new ArrayList<>();
        List<MotorcycleOutDTO> motorcycleDTOS = motorcycleService.getAllMotorcycles();
        List<CourseOutDTO> courseDTOS = courseService.getAllCourses();

        for(Owner owner : owners){
            OwnerOutDTO ownerOutDTO = new OwnerOutDTO(owner.getName(), owner.getEmail(), owner.getPhoneNumber(), owner.getAddress(),motorcycleDTOS, courseDTOS );
            ownerOutDTOList.add(ownerOutDTO);
        }
        return ownerOutDTOList;
    }


    public void createOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        o.setName(owner.getName());
        o.setEmail(owner.getEmail());
        o.setPhoneNumber(owner.getPhoneNumber());
        o.setPassword(owner.getPassword());

        ownerRepository.save(o);
    }

    public void deleteOwner(Integer id){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        ownerRepository.delete(o);

    }
    ////////////////













}
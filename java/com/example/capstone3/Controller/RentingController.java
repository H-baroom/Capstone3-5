package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Renting;
import com.example.capstone3.OutDTO.RentingDTO;
import com.example.capstone3.Service.RentingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/renting")
@RequiredArgsConstructor
public class RentingController {


    private final RentingService rentingService;


    @GetMapping("/get")
    public ResponseEntity getAllRentings(){
        List<RentingDTO> rentingDTOS = rentingService.getAllRentings();
        return ResponseEntity.status(200).body(rentingDTOS);
    }

    @PostMapping("/add/{owner_id}")
    public ResponseEntity addRenting(@PathVariable Integer owner_id, @RequestBody @Valid Renting renting) {

        rentingService.addRenting(owner_id, renting);
        return ResponseEntity.status(200).body(new ApiResponse("Renting added successfully!"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateRenting(@PathVariable Integer id, @RequestBody @Valid Renting renting){
        rentingService.updateRenting(id, renting);
        return ResponseEntity.status(200).body(new ApiResponse("Renting updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRenting(@PathVariable Integer id){

        rentingService.deleteRenting(id);
        return ResponseEntity.status(200).body(new ApiResponse("Renting deleted successfully!"));

    }
}
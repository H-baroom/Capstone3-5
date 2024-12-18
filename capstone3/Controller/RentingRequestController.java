package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.InDTO.RentingRequestInDTO;
import com.example.capstone3.Model.RentingRequest;
import com.example.capstone3.Service.RentingRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/motorcycle-system/renting-request")
@RequiredArgsConstructor
public class RentingRequestController {
    private final RentingRequestService rentingRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllRentingRequests() {
        return ResponseEntity.status(200).body(rentingRequestService.getAllRentingRequests());
    }

    @PostMapping("/add")
    public ResponseEntity addRentingRequest(@RequestBody @Valid RentingRequestInDTO rentingRequestInDTO) {
        rentingRequestService.addRentingRequest(rentingRequestInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Added "));
    }

    @PutMapping("/update/{rentingRequest_id}")
    public ResponseEntity updateRentingRequest(@PathVariable Integer rentingRequest_id,@RequestBody @Valid RentingRequestInDTO rentingRequestInDTO) {
        rentingRequestService.updateRentingRequest(rentingRequest_id, rentingRequestInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Updated "));
    }

    @DeleteMapping("/delete/{rentingRequest_id}")
    public ResponseEntity deleteRentingRequest(@PathVariable Integer rentingRequest_id) {
        rentingRequestService.deleteRentingRequest(rentingRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Deleted "));
    }

    @PutMapping("/extend-rental/{rentingRequestId}/{newEndDate}")
    public ResponseEntity extendRental(@PathVariable Integer rentingRequestId,@PathVariable LocalDate newEndDate) {
        rentingRequestService.extendRental(rentingRequestId,newEndDate);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Extend "));
    }
}

package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.InDTO.MaintenanceRequestInDTO;
import com.example.capstone3.Model.MaintenanceExpert;
import com.example.capstone3.OutDTO.MaintenanceRequestOutDTO;
import com.example.capstone3.Service.MaintenanceExpertService;
import com.example.capstone3.Service.MaintenanceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/maintenance-request")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllMaintenanceRequest(){
        List<MaintenanceRequestOutDTO> maintenanceRequestOutDTOS = maintenanceRequestService.getAllMaintenanceRequest();
        return ResponseEntity.status(200).body(maintenanceRequestOutDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity addMaintenanceRequest(@RequestBody @Valid MaintenanceRequestInDTO maintenanceRequestDTO_in){
        maintenanceRequestService.addMaintenanceRequest(maintenanceRequestDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest created!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenanceRequest(@PathVariable Integer id, @RequestBody @Valid MaintenanceRequestInDTO maintenanceRequestDTO_in){

        maintenanceRequestService.updateMaintenanceRequest(id, maintenanceRequestDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest updated!"));

    }

    @DeleteMapping("/delete/{maintenanceRequest_id}")
    public ResponseEntity deleteMaintenanceRequest(@PathVariable Integer maintenanceRequest_id){

        maintenanceRequestService.deleteMaintenanceRequest(maintenanceRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest deleted!"));

    }

    @PutMapping("/mark-request-completed/{maintenanceRequestId}/{expertName}")
    public ApiResponse completeMaintenanceRequest(
            @PathVariable Integer maintenanceRequestId,
            @PathVariable String expertName) {

        maintenanceRequestService.updateMaintenanceRequestStatusToCompleted(maintenanceRequestId, expertName);

        return new ApiResponse("Maintenance request marked as completed successfully!");
    }
}
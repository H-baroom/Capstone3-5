package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.Model.Motorcycle;
import com.example.capstone3.OutDTO.MotorcycleOutDTO;
import com.example.capstone3.Service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping("/get")
    public ResponseEntity getAllMotorcycles(){
        List<MotorcycleOutDTO> motorcycleOutDTOS = motorcycleService.getAllMotorcycles();
        return ResponseEntity.status(200).body(motorcycleOutDTOS);
    }


    @PostMapping("/add/{owner_id}")
    public ResponseEntity addMotorcycle(@PathVariable Integer owner_id, @RequestBody @Valid Motorcycle motorcycle) {
        motorcycleService.addMotorcycle(owner_id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle added successfully!"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMotorcycle(@PathVariable Integer id, @RequestBody @Valid Motorcycle motorcycle){
        motorcycleService.updateMotorcycle(id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMotorcycle(@PathVariable Integer id){
        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle deleted successfully!"));
    }

    @GetMapping("/available-motorcycles")
    public ResponseEntity getAvailableMotorcycles(){
        return ResponseEntity.status(200).body(motorcycleService.getAvailableMotorcycles());
    }

    @GetMapping("/motorcycles-for-sale")
    public ResponseEntity getMotorcyclesForSale(){
        return ResponseEntity.status(200).body(motorcycleService.getMotorcyclesForSale());
    }

    @PutMapping("/change-for-sale-status/{id}/{forSale}/{price}")
    public ResponseEntity changeForSaleStatus(@PathVariable Integer id,@PathVariable Boolean forSale,@PathVariable Double price){
        motorcycleService.changeForSaleStatus(id, forSale,price);
        return ResponseEntity.status(200).body(new ApiResponse("ForSaleStatus changed!"));
    }
    @PutMapping("/change-available-status/{id}/{isAvailable}")
    public ResponseEntity changeAvailableStatus(@PathVariable Integer id,@PathVariable Boolean isAvailable){
        motorcycleService.changeAvailableStatus(id, isAvailable);
        return ResponseEntity.status(200).body(new ApiResponse("AvailableStatus changed!"));
    }

    @PutMapping("/discount-motorcycle/{motorcycle_id}/{owner_id}/{discountRate}")
    public ResponseEntity discountMotorcycle(@PathVariable Integer motorcycle_id,@PathVariable Integer owner_id,@PathVariable Double discountRate){
        motorcycleService.discountMotorcycle(motorcycle_id,owner_id,discountRate);
        return ResponseEntity.status(200).body(new ApiResponse("Discount motorcycle added"));
    }

    @GetMapping("/average-price-for-same-brand-and-year/{brand}/{year}")
    public ResponseEntity CalculateAveragePriceForSameBrandAndYear(@PathVariable String brand,@PathVariable Integer year){
        return ResponseEntity.status(200).body(motorcycleService.CalculateAveragePriceForSameBrandAndYear(brand,year));
    }











    }
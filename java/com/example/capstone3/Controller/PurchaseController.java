package com.example.capstone3.Controller;

import com.example.capstone3.Api.ApiResponse;
import com.example.capstone3.InDTO.PurchaseInDTO;
import com.example.capstone3.Service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/motorcycle-system/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/get")
    public ResponseEntity getAllPurchases() {
        return ResponseEntity.status(200).body(purchaseService.getAllPurchases());
    }

    @PostMapping("/add")
    public ResponseEntity addPurchase(@RequestBody @Valid PurchaseInDTO purchaseInDTO) {
        purchaseService.addPurchase(purchaseInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("purchases added"));
    }


}

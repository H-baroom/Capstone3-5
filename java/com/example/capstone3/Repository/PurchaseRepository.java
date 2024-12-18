package com.example.capstone3.Repository;

import com.example.capstone3.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findPurchaseById(Integer id);
}

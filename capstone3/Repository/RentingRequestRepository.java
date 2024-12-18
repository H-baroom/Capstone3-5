package com.example.capstone3.Repository;

import com.example.capstone3.Model.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingRequestRepository extends JpaRepository<RentingRequest, Integer> {
    RentingRequest findRentingRequestById(Integer id);
}

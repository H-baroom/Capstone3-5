package com.example.capstone3.Repository;

import com.example.capstone3.Model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Integer> {
    MaintenanceRequest findMaintenanceRequestById(Integer id);
}

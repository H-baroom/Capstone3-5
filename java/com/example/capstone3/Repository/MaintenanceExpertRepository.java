package com.example.capstone3.Repository;

import com.example.capstone3.Model.MaintenanceExpert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceExpertRepository extends JpaRepository<MaintenanceExpert, Integer> {
    MaintenanceExpert findMaintenanceExpertById(Integer id);
    MaintenanceExpert findMaintenanceExpertByName(String name);
}

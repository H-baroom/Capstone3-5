package com.example.capstone3.Repository;

import com.example.capstone3.Model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
    Motorcycle findMotorcycleById(Integer id);


    List<Motorcycle> findMotorcycleByIsAvailable(Boolean isAvailable);

    List<Motorcycle> findMotorcycleByIsForSale(Boolean isForSale);

    List<Motorcycle> findMotorcycleByBrandAndYear(String brand, Integer year);
}

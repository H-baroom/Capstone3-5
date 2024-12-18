package com.example.capstone3.Repository;

import com.example.capstone3.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Owner findOwnerById(Integer id);

    Owner findOwnerByMotorcyclesId(Integer id);
}

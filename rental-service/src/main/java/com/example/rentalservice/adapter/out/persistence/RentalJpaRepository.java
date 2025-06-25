package com.example.rentalservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rentalservice.domain.Rental;

public interface RentalJpaRepository extends JpaRepository<Rental,Long>{
    
}

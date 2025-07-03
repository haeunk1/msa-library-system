package com.example.rentalservice.application.port.out;

import java.util.Optional;

import com.example.rentalservice.domain.Rental;

public interface RentalRepository {
    Rental save(Rental rental);

    Optional<Rental> findById(Long id);
}

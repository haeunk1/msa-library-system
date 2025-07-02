package com.example.rentalservice.application.port.out;

import com.example.rentalservice.domain.Rental;

public interface RentalRepository {
    Rental save(Rental rental);
}

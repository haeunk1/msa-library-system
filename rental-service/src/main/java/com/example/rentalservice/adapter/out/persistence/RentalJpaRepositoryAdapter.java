package com.example.rentalservice.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.rentalservice.application.port.out.RentalRepository;
import com.example.rentalservice.domain.Rental;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class RentalJpaRepositoryAdapter implements RentalRepository{
    private final RentalJpaRepository jpaRepository;

    @Override
    public Rental save(Rental rental) {
        return jpaRepository.save(rental);
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return jpaRepository.findById(id);
    }


}

package com.example.rentalservice.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.example.rentalservice.application.port.out.RentalRepository;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class RentalJpaRepositoryAdapter implements RentalRepository{
    private final RentalJpaRepository jpaRepository;


}

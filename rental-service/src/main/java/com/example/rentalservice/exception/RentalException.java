package com.example.rentalservice.exception;

import lombok.Getter;

@Getter
public class RentalException extends RuntimeException {
    public RentalException(String message){
        super(message);
    }
}

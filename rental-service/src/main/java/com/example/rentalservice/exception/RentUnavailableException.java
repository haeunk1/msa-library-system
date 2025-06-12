package com.example.rentalservice.exception;

public class RentUnavailableException extends RuntimeException{
    public RentUnavailableException(){}
    public RentUnavailableException(String message){
        super(message);
    }
}

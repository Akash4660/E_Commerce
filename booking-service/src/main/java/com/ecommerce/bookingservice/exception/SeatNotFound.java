package com.ecommerce.bookingservice.exception;

public class SeatNotFound extends RuntimeException {
    public SeatNotFound(String message) {
        super(message);
    }

}

package com.ecommerce.bookingservice.exception;

public class SeatIsLockedorBokkedException extends RuntimeException {
    public SeatIsLockedorBokkedException(String message) {
        super(message);
    }

}

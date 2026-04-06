package com.ecommerce.bookingservice.service;

import java.util.List;

import com.ecommerce.bookingservice.dto.ConfirmBookingRequest;
import com.ecommerce.bookingservice.dto.LockSeatRequest;
import com.ecommerce.bookingservice.entity.ShowSeat;

public interface BookingService {

    List<ShowSeat> getSeatsByShow(Long showId); 

    void lockSeats(LockSeatRequest request);

    String confirmBooking(ConfirmBookingRequest request);
}

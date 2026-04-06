package com.ecommerce.bookingservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.bookingservice.dto.ConfirmBookingRequest;
import com.ecommerce.bookingservice.dto.LockSeatRequest;
import com.ecommerce.bookingservice.entity.ShowSeat;
import com.ecommerce.bookingservice.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/shows/{showId}/seats")
    public List<ShowSeat> getSeatsByShow(@PathVariable Long showId) {
        return bookingService.getSeatsByShow(showId);
    }

    @PostMapping("/lock-seats")
    public String lockSeats(@RequestBody LockSeatRequest request) {
        bookingService.lockSeats(request);
        return "Seats locked successfully";
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestBody ConfirmBookingRequest request) {
        return bookingService.confirmBooking(request);
    }
}

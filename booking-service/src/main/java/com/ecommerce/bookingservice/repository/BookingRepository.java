package com.ecommerce.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.bookingservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}

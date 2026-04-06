package com.ecommerce.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.bookingservice.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

}

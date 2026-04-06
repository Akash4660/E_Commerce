package com.ecommerce.bookingservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.bookingservice.client.ShowClient;
import com.ecommerce.bookingservice.dto.ConfirmBookingRequest;
import com.ecommerce.bookingservice.dto.LockSeatRequest;
import com.ecommerce.bookingservice.dto.ShowResponse;
import com.ecommerce.bookingservice.entity.Booking;
import com.ecommerce.bookingservice.entity.BookingSeat;
import com.ecommerce.bookingservice.entity.BookingStatus;
import com.ecommerce.bookingservice.entity.SeatStatus;
import com.ecommerce.bookingservice.entity.ShowSeat;
import com.ecommerce.bookingservice.exception.SeatIsLockedorBokkedException;
import com.ecommerce.bookingservice.exception.SeatNotFound;
import com.ecommerce.bookingservice.repository.BookingRepository;
import com.ecommerce.bookingservice.repository.BookingSeatRepository;
import com.ecommerce.bookingservice.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowClient showClient;

    @Override
    public List<ShowSeat> getSeatsByShow(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }

    @Override
    @Transactional
    public void lockSeats(LockSeatRequest request) {

        for (Long seatId : request.getSeatIds()) {
            ShowSeat seat = showSeatRepository.findByShowIdAndSeatId(request.getShowId(), seatId)
                    .orElseThrow(() -> new SeatNotFound("Seat not found"));

            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new SeatIsLockedorBokkedException("Seat already locked or booked " + seatId);
            }
            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedBy(request.getUserId());
            seat.setLockedAt(LocalDateTime.now());
            showSeatRepository.save(seat);
        }
    }

    // @Override
    // @Transactional
    // public String confirmBooking(ConfirmBookingRequest request) {
    // BigDecimal totalAmount = BigDecimal.ZERO;

    // // 1. Validate seats
    // for (Long seatId : request.getSeatIds()) {

    // ShowSeat seat = showSeatRepository
    // .findByShowIdAndSeatId(request.getShowId(), seatId)
    // .orElseThrow(() -> new SeatNotFound("Seat not found: " + seatId));

    // if (seat.getStatus() != SeatStatus.LOCKED ||
    // !seat.getLockedBy().equals(request.getUserId())) {

    // throw new SeatIsLockedorBokkedException("Seat not locked by user: " +
    // seatId);
    // }

    // // Dummy pricing (you can improve later)
    // totalAmount = totalAmount.add(BigDecimal.valueOf(200));
    // }
    // // 2. Create Booking
    // Booking booking = Booking.builder()
    // .userId(request.getUserId())
    // .showId(request.getShowId())
    // .totalAmount(totalAmount)
    // .status(BookingStatus.CONFIRMED)
    // .createdAt(LocalDateTime.now())
    // .build();

    // booking = bookingRepository.save(booking);

    // // 3. Map seats
    // for (Long seatId : request.getSeatIds()) {

    // ShowSeat seat = showSeatRepository
    // .findByShowIdAndSeatId(request.getShowId(), seatId)
    // .get();

    // BookingSeat bookingSeat = BookingSeat.builder()
    // .bookingId(booking.getId())
    // .showSeatId(seat.getId())
    // .build();

    // bookingSeatRepository.save(bookingSeat);

    // // 4. Update seat status
    // seat.setStatus(SeatStatus.BOOKED);
    // seat.setLockedBy(null);
    // seat.setLockedAt(null);

    // showSeatRepository.save(seat);
    // }

    // return "Booking confirmed with ID: " + booking.getId();

    // }

    @Override
    @Transactional
    public String confirmBooking(ConfirmBookingRequest request) {

        BigDecimal totalAmount = BigDecimal.ZERO;

        ShowResponse show;

        try {
            show = showClient.getShowById(request.getShowId());
        } catch (feign.FeignException.NotFound ex) {
            throw new RuntimeException("Show not found");
        } catch (Exception e) {
            throw new RuntimeException("Show service unavailable");
        }

        if (show == null || show.getId() == null) {
            throw new RuntimeException("Invalid show response");
        }

        BigDecimal pricePerSeat = show.getPrice();

        // 1. Validate seats
        for (Long seatId : request.getSeatIds()) {

            ShowSeat seat = showSeatRepository
                    .findByShowIdAndSeatId(request.getShowId(), seatId)
                    .orElseThrow(() -> new SeatNotFound("Seat not found: " + seatId));

            if (seat.getStatus() != SeatStatus.LOCKED ||
                    !seat.getLockedBy().equals(request.getUserId())) {

                throw new SeatIsLockedorBokkedException("Seat not locked by user: " + seatId);
            }

            // ✅ Dynamic pricing
            totalAmount = totalAmount.add(pricePerSeat);
        }

        // 2. Create Booking
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .showId(request.getShowId())
                .totalAmount(totalAmount)
                .status(BookingStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();

        booking = bookingRepository.save(booking);

        // 3. Map seats + update
        for (Long seatId : request.getSeatIds()) {

            ShowSeat seat = showSeatRepository
                    .findByShowIdAndSeatId(request.getShowId(), seatId)
                    .get();

            BookingSeat bookingSeat = BookingSeat.builder()
                    .bookingId(booking.getId())
                    .showSeatId(seat.getId())
                    .build();

            bookingSeatRepository.save(bookingSeat);

            seat.setStatus(SeatStatus.BOOKED);
            seat.setLockedBy(null);
            seat.setLockedAt(null);

            showSeatRepository.save(seat);
        }

        return "Booking confirmed with ID: " + booking.getId();
    }
}

package com.ecommerce.bookingservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ecommerce.bookingservice.entity.SeatStatus;
import com.ecommerce.bookingservice.entity.ShowSeat;
import com.ecommerce.bookingservice.repository.ShowSeatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatUnlockSchedular {

    private final ShowSeatRepository showSeatRepository;
      // Runs every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void unlockExpiredSeats() {

        LocalDateTime expiryTime = LocalDateTime.now().minusSeconds(30);

        List<ShowSeat> expiredSeats =
                showSeatRepository.findByStatusAndLockedAtBefore(
                        SeatStatus.LOCKED, expiryTime
                );

        if (expiredSeats.isEmpty()) return;

        for (ShowSeat seat : expiredSeats) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedBy(null);
            seat.setLockedAt(null);
        }

        showSeatRepository.saveAll(expiredSeats);

        log.info("Unlocked {} expired seats", expiredSeats.size());
    }
}

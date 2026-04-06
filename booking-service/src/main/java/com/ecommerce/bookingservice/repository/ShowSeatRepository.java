package com.ecommerce.bookingservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.ecommerce.bookingservice.entity.SeatStatus;
import com.ecommerce.bookingservice.entity.ShowSeat;

import jakarta.persistence.LockModeType;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShowSeat> findByShowIdAndSeatId(Long showId,Long seatId);

    List<ShowSeat> findByStatusAndLockedAtBefore(SeatStatus status, LocalDateTime time);
}

package com.ecommerce.bookingservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class LockSeatRequest {

    private Long userId;
    private Long showId;
    private List<Long> seatIds;
}

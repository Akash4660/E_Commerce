package com.ecommerce.bookingservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConfirmBookingRequest {

    private Long userId;
    private Long showId;
    private List<Long> seatIds;
}

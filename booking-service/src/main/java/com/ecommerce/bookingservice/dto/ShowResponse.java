package com.ecommerce.bookingservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ShowResponse {

    private Long id;
    private BigDecimal price;
    private Long movieId;
    private Long theatreId;
}

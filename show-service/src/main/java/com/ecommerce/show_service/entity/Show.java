package com.ecommerce.show_service.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;     // from Movie Service
    private Long theatreId;   // from Theatre Service
    private Long screenId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private BigDecimal price;

    private String city;
}

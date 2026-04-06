package com.ecommerce.show_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.show_service.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    
    List<Show> findByMovieId(Long movieId);

    List<Show> findByCity(String city);

    List<Show> findByTheatreId(Long theatreId);
}

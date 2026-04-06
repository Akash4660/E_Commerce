package com.ecommerce.show_service.service;

import java.util.List;

import com.ecommerce.show_service.entity.Show;

public interface ShowService {

    Show createShow(Show show);

    List<Show> getShowsByMovie(Long movieId);

    List<Show> getShowsByCity(String city);

    Show getShowById(Long showId);
    
}

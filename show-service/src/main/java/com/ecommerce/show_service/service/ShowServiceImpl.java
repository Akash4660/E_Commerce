package com.ecommerce.show_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.show_service.entity.Show;
import com.ecommerce.show_service.repository.ShowRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {


    private final ShowRepository showRepository;

     @Override
     public Show createShow(Show show) {
            return showRepository.save(show);
    }

    @Override
    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    @Override
    public List<Show> getShowsByCity(String city) {
        return showRepository.findByCity(city);
    }

    @Override
    public Show getShowById(Long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    

}
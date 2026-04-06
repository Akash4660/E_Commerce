package com.ecommerce.show_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.show_service.entity.Show;
import com.ecommerce.show_service.service.ShowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public Show createShow(@RequestBody Show show) {
        return showService.createShow(show);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/city/{city}")
    public List<Show> getShowsByCity(@PathVariable String city) {
        return showService.getShowsByCity(city);
    }

    @GetMapping("/{id}")
    public Show getShow(@PathVariable Long id) {
        return showService.getShowById(id);
    }
}
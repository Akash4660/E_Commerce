package com.ecommerce.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.bookingservice.dto.ShowResponse;

@FeignClient(name = "show-service", url = "http://localhost:8083")
public interface ShowClient {
  
    @GetMapping("/api/shows/{id}")
    ShowResponse getShowById(@PathVariable Long id);
}

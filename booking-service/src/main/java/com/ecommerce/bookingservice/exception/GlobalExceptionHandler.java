package com.ecommerce.bookingservice.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatIsLockedorBokkedException.class)
    public ResponseEntity<Map<String,Object>> handleSeatLocked(SeatIsLockedorBokkedException ex){
        
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp",LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);                                                                                                            
    }

    @ExceptionHandler(SeatNotFound.class)
    public ResponseEntity<Map<String,Object>> handleSeatNotFound(SeatNotFound ex){      

        Map<String,Object> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp",LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);                                                                                                            
    }
}

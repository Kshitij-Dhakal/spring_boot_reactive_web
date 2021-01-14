package com.example.demo.exceptions;

import com.google.common.collect.Maps;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;

import java.util.Map;

import static com.example.demo.utility.Utility.timestamp;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvisor {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> duplicate(DuplicateResourceException ex) {
        Map<String, Object> error = error();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> invalid(InvalidRequestException ex) {
        Map<String, Object> error = error();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> notFound(NoDataFoundException ex) {
        Map<String, Object> error = error();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> error() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("error", true);
        map.put("timestamp", timestamp());
        return map;
    }
}

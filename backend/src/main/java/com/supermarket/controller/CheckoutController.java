package com.supermarket.controller;

import com.supermarket.dto.CheckoutRequestDto;
import com.supermarket.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller to handle checkout operations (scanning and getting total).
 */
@RestController
@RequestMapping("/api")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * Endpoint to process a complete checkout with a list of items.
     * Calculates the total price based on the provided items.
     *
     * @param request
     * @return
     */
    @PostMapping("/checkout")
    public Map<String, Integer> checkout(@RequestBody CheckoutRequestDto request) {
        int total = checkoutService.updateItems(request.getItems());
        return Collections.singletonMap("total", total);
    }

    record OfferRule(int quantity, int price) {
    }

    record ItemPrice(String name, int unitPrice, OfferRule offer) {
    }
}

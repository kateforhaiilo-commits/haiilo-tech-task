package com.supermarket.controller;

import com.supermarket.dto.CheckoutRequestDto;
import com.supermarket.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
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
    public Map<String, BigDecimal> checkout(@RequestBody CheckoutRequestDto request) {
        BigDecimal totalPrice = checkoutService.updateItems(request.getItems());
        return Collections.singletonMap("total", totalPrice);
    }

    record OfferRule(int quantity, BigDecimal price) {
    }

    record ItemPrice(String name, BigDecimal unitPrice, OfferRule offer) {
    }
}

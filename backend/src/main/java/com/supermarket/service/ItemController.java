package com.supermarket.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Allow frontend to connect
public class ItemController {

    private final PricingService pricingService;

    public ItemController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/items")
    public List<ItemDto> getItems() {
        AtomicLong idCounter = new AtomicLong();
        return pricingService.getAllPricing().values().stream()
                .map(itemPrice -> new ItemDto(idCounter.incrementAndGet(), itemPrice.name(), itemPrice.unitPrice()))
                .collect(Collectors.toList());
    }
}
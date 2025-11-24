package com.supermarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.service.ItemDto;
import com.supermarket.service.PricingService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final PricingService pricingService;

    public ItemController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping
    public List<ItemDto> getItems() {
        AtomicLong idCounter = new AtomicLong();
        return pricingService.getAllPricing().values().stream()
                .map(itemPrice -> toDto(itemPrice, idCounter.incrementAndGet()))
                .collect(Collectors.toList());
    }

    private ItemDto toDto(com.supermarket.service.ItemPrice itemPrice, long id) {
        // This is a simplified mapping. Consider using a library like MapStruct for
        // more complex applications.
        // For now, we manually create the DTO.
        return new ItemDto(id, itemPrice.name(), itemPrice.unitPrice(), itemPrice.offer(), 0);
    }
}

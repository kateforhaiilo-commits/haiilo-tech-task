package com.supermarket.dto;

import java.util.List;

import com.supermarket.service.ItemDto;

public class CheckoutRequestDto {
    private List<ItemDto> items;

    public List<ItemDto> getItems() {
        return items;
    }
}
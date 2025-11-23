package com.supermarket.service;

import com.supermarket.service.ItemDto;

public class ItemDto {
    private long id;
    private String name;
    private int price;
    private OfferRule offer;
    private int quantity;

    public ItemDto(long id, String name, int price, OfferRule offer, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.offer = offer;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public OfferRule getOffer() {
        return offer;
    }

    public int getQuantity() {
        return quantity;
    }
}
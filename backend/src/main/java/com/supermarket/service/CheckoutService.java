package com.supermarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service containing the core business logic for calculating the basket total.
 */
@Service
public class CheckoutService {

    private final PricingService pricingService;

    @Autowired
    public CheckoutService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public BigDecimal updateItems(List<ItemDto> items) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemDto item : items) {
            ItemPrice priceInfo = pricingService.getPrice(item.getName());
            if (priceInfo != null) {
                totalPrice = totalPrice.add(calculateItemTotal(priceInfo, item.getQuantity()));
            }
        }
        return totalPrice;
    }

    private BigDecimal calculateItemTotal(ItemPrice priceInfo, int quantity) {
        OfferRule offer = priceInfo.offer();
        if (offer != null && quantity >= offer.quantity()) {
            int offerSets = quantity / offer.quantity();
            int remainingItems = quantity % offer.quantity();
            return offer.price()
                    .multiply(BigDecimal.valueOf(offerSets))
                    .add(priceInfo.unitPrice().multiply(BigDecimal.valueOf(remainingItems)));
        }
        return priceInfo.unitPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

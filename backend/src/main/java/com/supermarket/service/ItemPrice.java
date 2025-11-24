package com.supermarket.service;

import java.math.BigDecimal;

/**
 * Data model for an individual item, including its unit price and special
 * offer.
 *
 * @param name      The unique name of the item (e.g., "Apple").
 * @param unitPrice The standard price for one item.
 * @param offer     The special offer rule, or null if no offer exists.
 * @param quantity  The quantity, how many items are selected.
 */
public record ItemPrice(String name, BigDecimal unitPrice, OfferRule offer, int quantity) {
}

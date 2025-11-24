package com.supermarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private PricingService pricingService;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        // MockitoExtension handles mock initialization
    }

    @Test
    void updateItems_emptyList_returnsZero() {
        BigDecimal total = checkoutService.updateItems(List.of());
        assertEquals(0, total.compareTo(BigDecimal.ZERO));
    }

    @Test
    void updateItems_priceNotFound_itemIgnored() {
        ItemDto item = mock(ItemDto.class);
        when(item.getName()).thenReturn("UNKNOWN");
        when(pricingService.getPrice("UNKNOWN")).thenReturn(null);

        BigDecimal total = checkoutService.updateItems(List.of(item));
        assertEquals(0, total.compareTo(BigDecimal.ZERO));
        verify(pricingService).getPrice("UNKNOWN");
    }

    @Test
    void updateItems_noOffer_calculatesUnitTotal() {
        ItemDto item = mock(ItemDto.class);
        when(item.getName()).thenReturn("APPLE");
        when(item.getQuantity()).thenReturn(4);

        ItemPrice priceInfo = mock(ItemPrice.class);
        when(priceInfo.unitPrice()).thenReturn(new BigDecimal("0.50"));
        when(priceInfo.offer()).thenReturn(null);

        when(pricingService.getPrice("APPLE")).thenReturn(priceInfo);

        BigDecimal total = checkoutService.updateItems(List.of(item));
        BigDecimal expected = new BigDecimal("2.00"); // 4 * 0.50
        assertEquals(0, total.compareTo(expected));
    }

    @Test
    void updateItems_withOffer_appliesOfferAndRemainder() {
        ItemDto item = mock(ItemDto.class);
        when(item.getName()).thenReturn("SARDINE");
        when(item.getQuantity()).thenReturn(7);

        OfferRule offer = mock(OfferRule.class);
        when(offer.quantity()).thenReturn(3);
        when(offer.price()).thenReturn(new BigDecimal("5.00")); // price for 3 items

        ItemPrice priceInfo = mock(ItemPrice.class);
        when(priceInfo.offer()).thenReturn(offer);
        when(priceInfo.unitPrice()).thenReturn(new BigDecimal("2.00")); // single unit price

        when(pricingService.getPrice("SARDINE")).thenReturn(priceInfo);

        BigDecimal total = checkoutService.updateItems(List.of(item));
        // 7 items => 2 full offers (2 * 5.00) + 1 remaining * 2.00 = 10.00 + 2.00 =
        // 12.00
        BigDecimal expected = new BigDecimal("12.00");
        assertEquals(0, total.compareTo(expected));
    }

    @Test
    void updateItems_multipleDifferentItems_sumOfTotals() {
        ItemDto a = mock(ItemDto.class);
        when(a.getName()).thenReturn("A");
        when(a.getQuantity()).thenReturn(2);

        ItemDto b = mock(ItemDto.class);
        when(b.getName()).thenReturn("B");
        when(b.getQuantity()).thenReturn(5);

        ItemPrice priceA = mock(ItemPrice.class);
        when(priceA.unitPrice()).thenReturn(new BigDecimal("1.00"));
        when(priceA.offer()).thenReturn(null);

        OfferRule offerB = mock(OfferRule.class);
        when(offerB.quantity()).thenReturn(2);
        when(offerB.price()).thenReturn(new BigDecimal("3.00")); // 2 for 3

        ItemPrice priceB = mock(ItemPrice.class);
        when(priceB.unitPrice()).thenReturn(new BigDecimal("2.00"));
        when(priceB.offer()).thenReturn(offerB);

        when(pricingService.getPrice("A")).thenReturn(priceA);
        when(pricingService.getPrice("B")).thenReturn(priceB);

        BigDecimal total = checkoutService.updateItems(List.of(a, b));
        // A: 2 * 1.00 = 2.00
        // B: 5 items => 2 sets of offer (2 * 3.00 = 6.00) + 1 remaining * 2.00 = 8.00
        BigDecimal expected = new BigDecimal("10.00");
        assertEquals(0, total.compareTo(expected));
    }
}
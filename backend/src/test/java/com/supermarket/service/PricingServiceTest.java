package com.supermarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
    }

    @Test
    void returnsAllExpectedItems() {
        Map<String, ItemPrice> all = pricingService.getAllPricing();
        assertNotNull(all);
        assertEquals(6, all.size());
        assertTrue(all.containsKey("Aurora Apple"));
        assertTrue(all.containsKey("Blazing Banana"));
        assertTrue(all.containsKey("Shimmering Starfruit"));
        assertTrue(all.containsKey("Kismet Kiwi"));
        assertTrue(all.containsKey("Prism Pear"));
        assertTrue(all.containsKey("Celestial Carrot"));
    }

    @Test
    void getPriceReturnsSameInstanceAsMapEntry() {
        ItemPrice fromGet = pricingService.getPrice("Aurora Apple");
        Map<String, ItemPrice> all = pricingService.getAllPricing();
        assertNotNull(fromGet);
        assertSame(all.get("Aurora Apple"), fromGet);
    }

    @Test
    void unknownItemReturnsNull() {
        assertNull(pricingService.getPrice("Nonexistent Item"));
    }

    @Test
    void pricingMapIsUnmodifiable() {
        Map<String, ItemPrice> all = pricingService.getAllPricing();
        assertThrows(UnsupportedOperationException.class, () -> all.put("New Item", null));
    }
}
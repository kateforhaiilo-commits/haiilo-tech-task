package com.supermarket.controller;

import com.supermarket.service.ItemDto;
import com.supermarket.service.ItemPrice;
import com.supermarket.service.PricingService;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Test
    void getItems_mapsAllPricingValues_andInvokesItemPriceAccessors() throws Exception {
        PricingService pricingService = mock(PricingService.class);
        ItemPrice itemA = mock(ItemPrice.class);
        ItemPrice itemB = mock(ItemPrice.class);

        when(itemA.name()).thenReturn("Apple");
        when(itemB.name()).thenReturn("Banana");

        // preserve insertion order so we can assert id ordering
        Map<String, ItemPrice> pricingMap = new LinkedHashMap<>();
        pricingMap.put("A", itemA);
        pricingMap.put("B", itemB);

        when(pricingService.getAllPricing()).thenReturn(pricingMap);

        ItemController controller = new ItemController(pricingService);

        List<ItemDto> dtos = controller.getItems();

        assertEquals(2, dtos.size(), "expected two DTOs returned");

        // verify the controller called the accessors on each ItemPrice
        verify(itemA, atLeastOnce()).name();
        verify(itemA, atLeastOnce()).unitPrice();
        verify(itemA, atLeastOnce()).offer();

        verify(itemB, atLeastOnce()).name();
        verify(itemB, atLeastOnce()).unitPrice();
        verify(itemB, atLeastOnce()).offer();

        // check mapped properties (name and incremental ids) via reflection to avoid
        // relying on DTO implementation details
        Object name0 = readProperty(dtos.get(0), "name");
        Object name1 = readProperty(dtos.get(1), "name");
        assertEquals("Apple", name0);
        assertEquals("Banana", name1);

        Number id0 = (Number) readProperty(dtos.get(0), "id");
        Number id1 = (Number) readProperty(dtos.get(1), "id");
        assertEquals(1L, id0.longValue());
        assertEquals(2L, id1.longValue());
    }

    // Reflection helper: tries getX(), x(), or direct field access for the given
    // property name.
    private Object readProperty(Object obj, String propName) throws Exception {
        Class<?> cls = obj.getClass();
        String capitalized = Character.toUpperCase(propName.charAt(0)) + propName.substring(1);

        // try getProp()
        try {
            Method m = cls.getMethod("get" + capitalized);
            return m.invoke(obj);
        } catch (NoSuchMethodException ignored) {
        }

        // try prop()
        try {
            Method m = cls.getMethod(propName);
            return m.invoke(obj);
        } catch (NoSuchMethodException ignored) {
        }

        // try field access
        try {
            Field f = cls.getDeclaredField(propName);
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException ignored) {
        }

        throw new NoSuchMethodException("No accessor or field found for property: " + propName);
    }
}
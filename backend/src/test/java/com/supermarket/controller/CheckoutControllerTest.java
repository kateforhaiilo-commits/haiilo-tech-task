package com.supermarket.controller;

import com.supermarket.dto.CheckoutRequestDto;
import com.supermarket.service.CheckoutService;
import com.supermarket.service.ItemDto;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutControllerTest {

    @Test
    void checkout_returns_total_from_service() {
        CheckoutService checkoutService = mock(CheckoutService.class);
        CheckoutController controller = new CheckoutController(checkoutService);

        CheckoutRequestDto request = mock(CheckoutRequestDto.class);
        ItemDto itemA = mock(ItemDto.class);
        ItemDto itemB = mock(ItemDto.class);
        List<ItemDto> items = List.of(itemA, itemB);
        when(request.getItems()).thenReturn(items);

        BigDecimal expectedTotal = new BigDecimal("12.34");
        when(checkoutService.updateItems(any())).thenReturn(expectedTotal);

        Map<String, BigDecimal> response = controller.checkout(request);

        assertNotNull(response);
        assertEquals(expectedTotal, response.get("total"));
        verify(checkoutService, times(1)).updateItems(items);
    }

    @Test
    void checkout_passes_request_items_to_service() {
        CheckoutService checkoutService = mock(CheckoutService.class);
        CheckoutController controller = new CheckoutController(checkoutService);

        CheckoutRequestDto request = mock(CheckoutRequestDto.class);
        ItemDto itemX = mock(ItemDto.class);
        ItemDto itemY = mock(ItemDto.class);
        ItemDto itemZ = mock(ItemDto.class);
        List<ItemDto> items = List.of(itemX, itemY, itemZ);
        when(request.getItems()).thenReturn(items);

        when(checkoutService.updateItems(any())).thenReturn(BigDecimal.ZERO);

        controller.checkout(request);

        ArgumentCaptor<List<ItemDto>> captor = ArgumentCaptor.forClass(List.class);
        verify(checkoutService).updateItems(captor.capture());
        assertEquals(items, captor.getValue());
    }
}
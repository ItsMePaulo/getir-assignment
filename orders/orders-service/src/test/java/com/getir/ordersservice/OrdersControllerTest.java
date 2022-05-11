package com.getir.ordersservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.ordersapi.model.AddressDto;
import com.getir.ordersapi.model.ProductItemDto;
import com.getir.ordersservice.documents.Address;
import com.getir.ordersservice.documents.ProductItem;
import com.getir.ordersapi.model.OrderStatus;
import com.getir.ordersapi.model.OrdersDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private final Supplier<OrdersDto> ordersDtoSupplier = () -> {
        var products = List.of(
                new ProductItemDto("1234", 5),
                new ProductItemDto("456", 1)
        );

        var address = new AddressDto("hello street", 12, "world", "1233");

        return OrdersDto.createOrderDto(
                UUID.fromString("66db3fc7-eb44-48cf-a955-7122992f6e65"),
                24.9,
                address,
                products
        );
    };

    @Test
    void shouldCreateOrder() throws Exception {
        var order = createOrder();

        deleteOrder(order.getId());
    }

    @Test
    void shouldUpdateStatus() throws Exception {
        var order = createOrder();

        var result = mockMvc.perform(patch(String.format("/orders/%s", order.getId()))
                        .param("status", String.valueOf(OrderStatus.ON_ROUTE))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        var orderResponse = mapper.readValue(result.getResponse().getContentAsString(), OrdersDto.class);
        assertEquals(OrderStatus.ON_ROUTE, orderResponse.getStatus());

        deleteOrder(orderResponse.getId());
    }

    @Test
    void shouldFetchOrder() throws Exception {
        var order = createOrder();

        var result = mockMvc.perform(get(String.format("/orders/%s", order.getId()))
                ).andExpect(status().isOk())
                .andReturn();

        var orderDtoResult = mapper.readValue(result.getResponse().getContentAsString(), OrdersDto.class);

        assertEquals(OrderStatus.RECEIVED, orderDtoResult.getStatus());
        assertNotNull(orderDtoResult.getCreatedAt());
        assertNotNull(orderDtoResult.getLastUpdatedAt());

        deleteOrder(order.getId());
    }

    @Test
    void shouldReturnOrderNotFound() throws Exception {

        mockMvc.perform(patch("/orders/66db3fc7-eb44-48cf-a955-7122992f6e65?status=IN_PROGRESS")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        mockMvc.perform(get("/orders/66db3fc7-eb44-48cf-a955-7122992f6e65"))
                .andExpect(status().isNotFound());
    }

    private OrdersDto createOrder() throws Exception {
        var ordersDto = ordersDtoSupplier.get();

        var result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ordersDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        var orderDtoResult = mapper.readValue(result.getResponse().getContentAsString(), OrdersDto.class);

        assertEquals(OrderStatus.RECEIVED, orderDtoResult.getStatus());
        assertNotNull(orderDtoResult.getCreatedAt());
        assertNotNull(orderDtoResult.getLastUpdatedAt());

        return orderDtoResult;
    }

    private void deleteOrder(UUID id) throws Exception {
        mockMvc.perform(delete(String.format("/orders/%s", id)))
                .andExpect(status().isOk());
    }

}

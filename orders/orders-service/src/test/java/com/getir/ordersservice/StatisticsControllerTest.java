package com.getir.ordersservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.ordersservice.documents.Address;
import com.getir.ordersservice.documents.ProductItem;
import com.getir.ordersservice.model.OrderStatus;
import com.getir.ordersservice.model.OrdersDto;
import com.getir.ordersservice.model.StatisticsDto;
import com.getir.ordersservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {

    public static final String USER_ID = "66db3fc7-eb44-48cf-a955-7122992f6e65";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    OrderRepository orderRepository;

    private final Supplier<OrdersDto> ordersDtoSupplier = () -> {
        var products = List.of(
                new ProductItem("1234", 5),
                new ProductItem("456", 1)
        );

        var address = new Address("hello street", 12, "world", "1233");

        return OrdersDto.createOrderDto(
                UUID.fromString(USER_ID),
                24.9,
                address,
                products
        );
    };

    @BeforeEach
    void setupDb() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldCalculateOrders() throws Exception {

        var orderDtoResults = createOrders();
        updateOrder(orderDtoResults);

        var statistics = mockMvc.perform(get("/statistics")).andExpect(status().isOk())
                .andReturn();

        Map<Month, StatisticsDto> statisticsResult = mapper.readValue(
                statistics.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        var stats = statisticsResult.values().stream().findFirst().orElseThrow(Exception::new);

        assertEquals(2, stats.getOrders());
        assertEquals((24.9 * 2), stats.getTotalAmount());
        assertEquals(12, stats.getTotalProducts());

        for (OrdersDto order : orderDtoResults) {
            mockMvc.perform(delete(String.format("/orders/%s", order.getId())))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void shouldCalculateOrdersForSingleUser() throws Exception {

        var orderDtoResults = createOrders();
        updateOrder(orderDtoResults);

        var statistics = mockMvc.perform(get("/statistics/" + USER_ID))
                .andExpect(status().isOk())
                .andReturn();

        Map<Month, StatisticsDto> statisticsResult = mapper.readValue(
                statistics.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        var stats = statisticsResult.values().stream().findFirst().orElseThrow(Exception::new);

        assertEquals(1, stats.getOrders());
        assertEquals(24.9, stats.getTotalAmount());
        assertEquals(6, stats.getTotalProducts());

        for (OrdersDto order : orderDtoResults) {
            mockMvc.perform(delete(String.format("/orders/%s", order.getId())))
                    .andExpect(status().isOk());
        }
    }

    private void updateOrder(List<OrdersDto> orders) throws Exception {

        for (OrdersDto order : orders) {
            mockMvc.perform(patch(String.format("/orders/%s", order.getId()))
                            .param("status", String.valueOf(OrderStatus.DELIVERED))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }


    private List<OrdersDto> createOrders() throws Exception {
        var ordersDto = ordersDtoSupplier.get();
        var ordersDto2 = ordersDtoSupplier.get();

        ordersDto2.setUserId(UUID.randomUUID());

        return List.of(
                performCreate(ordersDto),
                performCreate(ordersDto2)
        );
    }

    private OrdersDto performCreate(OrdersDto ordersDto) throws Exception {
        var result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ordersDto))
                ).andExpect(status().isOk())
                .andReturn();

        return mapper.readValue(result.getResponse().getContentAsString(), OrdersDto.class);
    }
}

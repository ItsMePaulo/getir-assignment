package com.getir.ordersapi;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@Configuration
@Import(FeignAutoConfiguration.class)
public class OrdersApiApplication {

}

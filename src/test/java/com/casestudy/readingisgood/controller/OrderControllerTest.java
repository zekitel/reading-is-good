package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDTO;
import com.casestudy.readingisgood.security.JwtRequestDto;
import com.casestudy.readingisgood.security.JwtResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderController orderController;

    @Captor
    private ArgumentCaptor<OrderRequestDTO> orderRequestDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;


    @Captor
    private ArgumentCaptor<OrderTimeIntervalsRequestDTO> orderTimeIntervalsRequestDtoArgumentCaptor;


    @Captor
    private ArgumentCaptor<CustomerOrdersRequestDTO> customerOrdersRequestDtoArgumentCaptor;

    private final ObjectMapper objectMapper = new ObjectMapper();
    String token;

    @BeforeEach
    void init() throws Exception {

        JwtRequestDto jwtRequestDto = new JwtRequestDto("user", "password");

        String result = mockMvc.perform(post("/authenticate")
                .content(objectMapper.writeValueAsBytes(jwtRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        JwtResponseDto jwtResponseDto = new ObjectMapper().readValue(result, JwtResponseDto.class);

        token = "Bearer " + jwtResponseDto.getJwttoken();
    }

    @Test
    void create() throws Exception {

        OrderDTO orderDto = OrderDTO.builder()
                .customerId(1L)
                .bookId(1L)
                .bookCount(2L)
                .build();
        when(orderController.create(orderRequestDtoArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(orderDto));

        mockMvc.perform(post("/api/order/create")
                        .content(objectMapper.writeValueAsBytes(orderDto))
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        OrderRequestDTO value = orderRequestDtoArgumentCaptor.getValue();
        assertThat(value.getBookCount()).isEqualTo(orderDto.getBookCount());
        assertThat(value.getCustomerId()).isEqualTo(orderDto.getCustomerId());
        assertThat(value.getBookId()).isEqualTo(orderDto.getBookId());


    }

    @Test
    void get_order() throws Exception {

        int id = 1;

        OrderDTO orderDto = OrderDTO.builder()
                .customerId(1L)
                .bookId(1L)
                .bookCount(2L)
                .build();

        when(orderController.get(longArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(orderDto));

        mockMvc.perform(get("/api/order/"+id)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long value = longArgumentCaptor.getValue();
        assertThat(value.intValue()).isEqualTo(id);

    }

    @Test
    void listOrdersByDateInterval() throws Exception {

        OrderTimeIntervalsRequestDTO orderTimeIntervalsRequestDto = new OrderTimeIntervalsRequestDTO();
        orderTimeIntervalsRequestDto.setPageNumber(0);
        orderTimeIntervalsRequestDto.setPageSize(5);
        orderTimeIntervalsRequestDto.setEndDateTime(LocalDateTime.now());
        orderTimeIntervalsRequestDto.setStartDateTime(LocalDateTime.now().minus(10, ChronoUnit.MINUTES));

        when(orderController.listByDateInterval(orderTimeIntervalsRequestDtoArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(post("/api/order/list-by-date-interval")
                        .content(objectMapper.writeValueAsBytes(orderTimeIntervalsRequestDto))
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        OrderTimeIntervalsRequestDTO value = orderTimeIntervalsRequestDtoArgumentCaptor.getValue();


        assertThat(value.getEndDateTime()).isEqualTo(orderTimeIntervalsRequestDto.getEndDateTime());
        assertThat(value.getStartDateTime()).isEqualTo(orderTimeIntervalsRequestDto.getStartDateTime());
        assertThat(value.getPageNumber()).isEqualTo(orderTimeIntervalsRequestDto.getPageNumber());
        assertThat(value.getPageSize()).isEqualTo(orderTimeIntervalsRequestDto.getPageSize());


    }


    @Test
    void listOrderByCustomer() throws Exception {

        CustomerOrdersRequestDTO orderTimeIntervalsRequestDto = new CustomerOrdersRequestDTO();
        orderTimeIntervalsRequestDto.setPageNumber(0);
        orderTimeIntervalsRequestDto.setPageSize(5);
        orderTimeIntervalsRequestDto.setCustomerId(1L);

        when(orderController.listByCustomer(customerOrdersRequestDtoArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(post("/api/order/list-by-customer")
                        .content(objectMapper.writeValueAsBytes(orderTimeIntervalsRequestDto))
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerOrdersRequestDTO value = customerOrdersRequestDtoArgumentCaptor.getValue();

        assertThat(value.getCustomerId()).isEqualTo(orderTimeIntervalsRequestDto.getCustomerId());
        assertThat(value.getPageNumber()).isEqualTo(orderTimeIntervalsRequestDto.getPageNumber());
        assertThat(value.getPageSize()).isEqualTo(orderTimeIntervalsRequestDto.getPageSize());


    }
}

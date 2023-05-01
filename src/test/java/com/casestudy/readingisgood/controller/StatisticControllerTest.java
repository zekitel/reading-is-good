package com.casestudy.readingisgood.controller;

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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class StatisticControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticController statisticController;


    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
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
    void monthlyOrderStatistics() throws Exception {

        long customerId = 1L;

        when(statisticController.monthlyOrder(longArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(get("/api/statistic/monthly-order/"+customerId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long value = longArgumentCaptor.getValue();
        assertThat(value.longValue()).isEqualTo(customerId);


    }
}

package com.casestudy.readingisgood.controller;

import com.casestudy.readingisgood.dto.BookDTO;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookController bookController;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Captor
    private ArgumentCaptor<BookDTO> bookDtoArgumentCaptor;


    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longStockArgumentCaptor;
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
    void createNewBook() throws Exception {

        BookDTO book1 = BookDTO.builder()
                .title("Book1")
                .author("Author1")
                .isbn("11231334561")
                .price(BigDecimal.valueOf(10.0))
                .stock(4L)
                .build();

        when(bookController.create(bookDtoArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(book1));

        mockMvc.perform(post("/api/book/create")
                        .content(objectMapper.writeValueAsBytes(book1))
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        BookDTO capture = bookDtoArgumentCaptor.getValue();
        assertThat(capture.getStock()).isEqualTo(book1.getStock());
        assertThat(capture.getPrice()).isEqualTo(book1.getPrice());
        assertThat(capture.getIsbn()).isEqualTo(book1.getIsbn());
        assertThat(capture.getTitle()).isEqualTo(book1.getTitle());
        assertThat(capture.getAuthor()).isEqualTo(book1.getAuthor());

    }

    @Test
    void updateBookStock() throws Exception {


        int id = 1;
        int stock = 40;

        when(bookController.updateStock(longArgumentCaptor.capture(), longStockArgumentCaptor.capture())).thenReturn(ResponseEntity.ok(new BookDTO()));

        mockMvc.perform(put("/api/book/update-stock/"+id)
                .header("Authorization", token)
                .param("stock", Integer.toString(stock))
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        Long captureBookId = longArgumentCaptor.getValue();
        Long captureStock = longStockArgumentCaptor.getValue();
        assertThat(captureBookId.intValue()).isEqualTo(id);
        assertThat(captureStock.intValue()).isEqualTo(stock);

    }
}
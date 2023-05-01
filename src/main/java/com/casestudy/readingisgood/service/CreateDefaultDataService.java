package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.BookDTO;
import com.casestudy.readingisgood.dto.CustomerDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.ResourceAlreadyExistsException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CreateDefaultDataService {

    private final CustomerService customerService;
    private final BookService bookService;
    private final OrderService orderService;

    @PostConstruct
    public void init() throws DbNotFoundException, StockIsNotSufficientException, ResourceAlreadyExistsException, StockValueChangedException {
        BookDTO book1 = BookDTO.builder()
                .title("Book1")
                .author("Author1")
                .isbn("12334561")
                .price(BigDecimal.valueOf(10.0))
                .stock(4L)
                .build();


        BookDTO book2 = BookDTO.builder()
                .title("Book2")
                .author("Author2")
                .isbn("53245234")
                .price(BigDecimal.valueOf(19.0))
                .stock(12L)
                .build();

        BookDTO book3 = BookDTO.builder()
                .title("Book3")
                .author("Author2")
                .isbn("76575633")
                .price(BigDecimal.valueOf(80.0))
                .stock(3L)
                .build();

        BookDTO book4 = BookDTO.builder()
                .title("Book4")
                .author("Author3")
                .isbn("99723233")
                .price(BigDecimal.valueOf(50.0))
                .stock(9L)
                .build();

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .email("abc@gmail.com")
                .firstName("Name1")
                .lastName("LastName1")
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .email("aba.sdac@gmail.com")
                .firstName("Name2")
                .lastName("LastName2")
                .build();

        OrderRequestDTO order1 = OrderRequestDTO.builder()
                .bookId(1L)
                .customerId(1L)
                .bookCount(2L)
                .build();

        OrderRequestDTO order2 = OrderRequestDTO.builder()
                .bookId(2L)
                .customerId(2L)
                .bookCount(1L)
                .build();
        OrderRequestDTO order3 = OrderRequestDTO.builder()
                .bookId(3L)
                .customerId(1L)
                .bookCount(2L)
                .build();

        OrderRequestDTO order4 = OrderRequestDTO.builder()
                .bookId(4L)
                .customerId(2L)
                .bookCount(1L)
                .build();
        bookService.persist(book1);
        bookService.persist(book2);
        bookService.persist(book3);
        bookService.persist(book4);

        customerService.create(customerDTO1);
        customerService.create(customerDTO2);

        orderService.create(order1);
        orderService.create(order2);
        orderService.create(order3);
        orderService.create(order4);

    }
}

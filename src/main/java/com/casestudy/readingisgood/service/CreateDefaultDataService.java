package com.casestudy.readingisgood.service;

import com.casestudy.readingisgood.dto.BookDto;
import com.casestudy.readingisgood.dto.CustomerDto;
import com.casestudy.readingisgood.dto.OrderRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateDefaultDataService {

    private final CustomerService customerService;
    private final BookService bookService;
    private final OrderService orderService;

    @PostConstruct
    public void init() {
        BookDto book1 = BookDto.builder()
                .title("Book1")
                .author("Author1")
                .isbn("12334561")
                .price(10.0)
                .stock(4L)
                .build();


        BookDto book2 = BookDto.builder()
                .title("Book2")
                .author("Author2")
                .isbn("53245234")
                .price(19.0)
                .stock(12L)
                .build();

        BookDto book3 = BookDto.builder()
                .title("Book3")
                .author("Author2")
                .isbn("76575633")
                .price(80.0)
                .stock(3L)
                .build();

        BookDto book4 = BookDto.builder()
                .title("Book4")
                .author("Author3")
                .isbn("99723233")
                .price(50.0)
                .stock(9L)
                .build();

        CustomerDto customerDto1 = CustomerDto.builder()
                .email("abc@gmail.com")
                .firstName("Name1")
                .lastName("LastName1")
                .build();
        CustomerDto customerDto2 = CustomerDto.builder()
                .email("aba.sdac@gmail.com")
                .firstName("Name2")
                .lastName("LastName2")
                .build();

        OrderRequestDto order1 = OrderRequestDto.builder()
                .bookId(1L)
                .customerId(1L)
                .bookCount(2L)
                .build();

        OrderRequestDto order2 = OrderRequestDto.builder()
                .bookId(2L)
                .customerId(2L)
                .bookCount(1L)
                .build();
        OrderRequestDto order3 = OrderRequestDto.builder()
                .bookId(3L)
                .customerId(1L)
                .bookCount(2L)
                .build();

        OrderRequestDto order4 = OrderRequestDto.builder()
                .bookId(4L)
                .customerId(2L)
                .bookCount(1L)
                .build();
        bookService.createNewBook(book1);
        bookService.createNewBook(book2);
        bookService.createNewBook(book3);
        bookService.createNewBook(book4);

        customerService.create(customerDto1);
        customerService.create(customerDto2);

        orderService.create(order1);
        orderService.create(order2);
        orderService.create(order3);
        orderService.create(order4);

    }
}

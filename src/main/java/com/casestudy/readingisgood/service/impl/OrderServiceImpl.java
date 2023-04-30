package com.casestudy.readingisgood.service.impl;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDto;
import com.casestudy.readingisgood.dto.MonthlyStatisticDto;
import com.casestudy.readingisgood.dto.OrderDto;
import com.casestudy.readingisgood.dto.OrderRequestDto;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.entity.Customer;
import com.casestudy.readingisgood.entity.Order;
import com.casestudy.readingisgood.entity.OrderStatistic;
import com.casestudy.readingisgood.enums.OrderStatusEnum;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StartDateIsGreaterThanEndDateException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.repository.OrderRepository;
import com.casestudy.readingisgood.repository.OrderStatisticRepository;
import com.casestudy.readingisgood.service.BookService;
import com.casestudy.readingisgood.service.CustomerService;
import com.casestudy.readingisgood.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderStatisticRepository orderStatisticRepository;


    @SneakyThrows
    @Override
    @Transactional
    public OrderDto create(OrderRequestDto orderRequestDto) {
        log.info("Order crete started. Coming data: . OrderDto:{}", orderRequestDto);
        Optional<Customer> customerById = customerService.findCustomerById(orderRequestDto.getCustomerId());
        if (customerById.isEmpty()) {
            log.warn("No Order found with this id: " + orderRequestDto.getCustomerId());
            throw new DbNotFoundException("The book is not found");
        }

        Book book = bookService.findBookById(orderRequestDto.getBookId());
        if (book.getStock() < orderRequestDto.getBookCount()) {
            log.warn("There is no enough stock for this order. Order book count: {}, Book count :{}",
                    orderRequestDto.getBookCount(), book.getStock());
            throw new StockIsNotSufficientException("The stock of books is not enough for your order");
        }

        Order order = Order.builder()
                .customerId(orderRequestDto.getCustomerId())
                .orderAmount(book.getPrice() * orderRequestDto.getBookCount())
                .bookCount(orderRequestDto.getBookCount())
                .orderStatus(OrderStatusEnum.CREATED)
                .orderTime(LocalDateTime.now())
                .bookId(orderRequestDto.getBookId()).build();
        Order result = orderRepository.save(order);

        log.info("Order create finished. response data. Order:{}", result);


        Order savedOrder = orderRepository.save(order);

        OrderStatistic orderStatistic = OrderStatistic.builder()
                .orderMonth(savedOrder.getOrderTime().getYear() + "-" + savedOrder.getOrderTime().getMonth())
                .totalBookCount(savedOrder.getBookCount())
                .totalPurchasedAmount(savedOrder.getOrderAmount())
                .customerId(orderRequestDto.getCustomerId())
                .build();

        OrderStatistic savedOrderStatistic = orderStatisticRepository.save(orderStatistic);

        log.info("Order statistic persisted. OrderStatistic:{}", savedOrderStatistic);

        return modelMapper.map(result, OrderDto.class);
    }

    @SneakyThrows
    @Override
    @Transactional
    public OrderDto get(Long id) {
        log.info("Order get started. Coming data: . Order id :{}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException("No Order found with this id: " + id));
        log.info("Order get finished. Result data: . Order :{}", order);
        return modelMapper.map(order, OrderDto.class);
    }


    @Override
    @SneakyThrows
    public List<OrderDto> listOrdersByDateInterval(long startDate, long endDate, int pageNo, int pageSize) {

        LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(startDate), ZoneOffset.UTC);
        LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(endDate), ZoneOffset.UTC);
        log.info("Order listOrdersByDateInterval started. Coming Start date:{}, End date:{}", startDate, endDate);
        if (startDateTime.isAfter(endDateTime)) {
            log.warn("Start date cannot be greater than end date");
            throw new StartDateIsGreaterThanEndDateException("StartDate Cannot Be Greater Than EndDate");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Order> orders = orderRepository.findAllByOrderTimeBetween(startDateTime, endDateTime, pageable);
        List<OrderDto> orderDtoList = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();

        log.info("Order listOrdersByDateInterval finished. Response data size: {}", orderDtoList.size());
        return orderDtoList;
    }

    @Override
    @SneakyThrows
    public List<OrderDto> listOrderByCustomer(CustomerOrdersRequestDto customerOrdersRequestDto) {

        log.info("Order listOrderByCustomer started.CustomerId:{} PageNumber:{}, PageSize:{}", customerOrdersRequestDto.getCustomerId(), customerOrdersRequestDto.getPageNumber(), customerOrdersRequestDto.getPageSize());


        Pageable pageable = PageRequest.of(customerOrdersRequestDto.getPageNumber(), customerOrdersRequestDto.getPageSize());
        List<Order> orders = orderRepository.findAllByCustomerId(customerOrdersRequestDto.getCustomerId(), pageable);
        List<OrderDto> orderDtoList = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();

        log.info("Order listOrderByCustomer finished. Response data size: {}", orderDtoList.size());
        return orderDtoList;
    }

    @Override
    public List<MonthlyStatisticDto> monthlyOrderStatistics(Long customerId) {
        return orderStatisticRepository.monthlyStatistics(customerId);
    }
}

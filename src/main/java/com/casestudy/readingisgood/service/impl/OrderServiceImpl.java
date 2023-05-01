package com.casestudy.readingisgood.service.impl;

import com.casestudy.readingisgood.dto.CustomerOrdersRequestDTO;
import com.casestudy.readingisgood.dto.OrderDTO;
import com.casestudy.readingisgood.dto.OrderRequestDTO;
import com.casestudy.readingisgood.dto.OrderTimeIntervalsRequestDTO;
import com.casestudy.readingisgood.entity.Book;
import com.casestudy.readingisgood.entity.Customer;
import com.casestudy.readingisgood.entity.Order;
import com.casestudy.readingisgood.enums.OrderStatusEnum;
import com.casestudy.readingisgood.exception.DbNotFoundException;
import com.casestudy.readingisgood.exception.StartDateIsGreaterThanEndDateException;
import com.casestudy.readingisgood.exception.StockIsNotSufficientException;
import com.casestudy.readingisgood.exception.StockValueChangedException;
import com.casestudy.readingisgood.repository.BookRepository;
import com.casestudy.readingisgood.repository.OrderRepository;
import com.casestudy.readingisgood.service.BookService;
import com.casestudy.readingisgood.service.CustomerService;
import com.casestudy.readingisgood.service.OrderService;
import com.casestudy.readingisgood.service.StatisticService;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final CustomerService customerService;

    private final StatisticService statisticService;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;


    @Override
    @Transactional
    public OrderDTO create(OrderRequestDTO orderRequestDto) throws DbNotFoundException, StockIsNotSufficientException, StockValueChangedException {
        log.info("Order crete started. Coming data: . OrderDto:{}", orderRequestDto);
        Optional<Customer> customerById = customerService.findCustomerById(orderRequestDto.getCustomerId());
        if (customerById.isEmpty()) {
            log.warn("No Order found with this id: " + orderRequestDto.getCustomerId());
            throw new DbNotFoundException("The book is not found");
        }

        Book book = bookService.findById(orderRequestDto.getBookId());
        if (book.getStock() < orderRequestDto.getBookCount()) {
            log.warn("There is no enough stock for this order. Order book count: {}, Book count :{}",
                    orderRequestDto.getBookCount(), book.getStock());
            throw new StockIsNotSufficientException("The stock of books is not enough for your order");
        }
        try {
            book.setStock(book.getStock()-orderRequestDto.getBookCount());
            book = bookRepository.save(book);
        } catch (OptimisticLockException ex) {
            throw new StockValueChangedException("The book stock value has been changed");
        }


        Order order = Order.builder()
                .customerId(orderRequestDto.getCustomerId())
                .orderAmount(book.getPrice().multiply(BigDecimal.valueOf(orderRequestDto.getBookCount())))
                .bookCount(orderRequestDto.getBookCount())
                .orderStatus(OrderStatusEnum.CREATED)
                .orderTime(LocalDateTime.now())
                .bookId(orderRequestDto.getBookId()).build();
        Order result = orderRepository.save(order);

        log.info("Order create finished. response data. Order:{}", result);
        statisticService.persist(result);

        return modelMapper.map(result, OrderDTO.class);
    }


    @Override
    @Transactional
    public OrderDTO get(Long id) throws DbNotFoundException {
        log.info("Order get started. Coming data: . Order id :{}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException("No Order found with this id: " + id));
        log.info("Order get finished. Result data: . Order :{}", order);
        return modelMapper.map(order, OrderDTO.class);
    }


    @Override
    public List<OrderDTO> listByDateInterval(OrderTimeIntervalsRequestDTO orderTimeIntervalsRequestDTO) throws StartDateIsGreaterThanEndDateException {

        log.info("Order listOrdersByDateInterval started. Coming Start date:{}, End date:{}", orderTimeIntervalsRequestDTO.getStartDateTime(), orderTimeIntervalsRequestDTO.getEndDateTime());
        if (orderTimeIntervalsRequestDTO.getStartDateTime().isAfter(orderTimeIntervalsRequestDTO.getEndDateTime())) {
            log.warn("Start date cannot be greater than end date");
            throw new StartDateIsGreaterThanEndDateException("StartDate Cannot Be Greater Than EndDate");
        }

        Pageable pageable = PageRequest.of(orderTimeIntervalsRequestDTO.getPageNumber(), orderTimeIntervalsRequestDTO.getPageSize());
        List<Order> orders = orderRepository.findAllByOrderTimeBetween(orderTimeIntervalsRequestDTO.getStartDateTime(), orderTimeIntervalsRequestDTO.getEndDateTime(), pageable);
        List<OrderDTO> orderDTOList = orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).toList();

        log.info("Order listOrdersByDateInterval finished. Response data size: {}", orderDTOList.size());
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> listByCustomer(CustomerOrdersRequestDTO customerOrdersRequestDto) {

        log.info("Order listOrderByCustomer started.CustomerId:{} PageNumber:{}, PageSize:{}", customerOrdersRequestDto.getCustomerId(), customerOrdersRequestDto.getPageNumber(), customerOrdersRequestDto.getPageSize());


        Pageable pageable = PageRequest.of(customerOrdersRequestDto.getPageNumber(), customerOrdersRequestDto.getPageSize());
        List<Order> orders = orderRepository.findAllByCustomerId(customerOrdersRequestDto.getCustomerId(), pageable);
        List<OrderDTO> orderDTOList = orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).toList();

        log.info("Order listOrderByCustomer finished. Response data size: {}", orderDTOList.size());
        return orderDTOList;
    }
}

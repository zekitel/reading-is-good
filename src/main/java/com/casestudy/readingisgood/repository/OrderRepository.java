package com.casestudy.readingisgood.repository;

import com.casestudy.readingisgood.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByOrderTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    List<Order> findAllByCustomerId(Long customerId, Pageable pageable);
}

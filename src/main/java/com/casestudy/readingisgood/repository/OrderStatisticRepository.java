package com.casestudy.readingisgood.repository;

import com.casestudy.readingisgood.dto.MonthlyStatisticDto;
import com.casestudy.readingisgood.entity.OrderStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderStatisticRepository extends JpaRepository<OrderStatistic, Long> {

    @Query("SELECT NEW com.casestudy.readingisgood.dto.MonthlyStatisticDto(o.orderMonth,COUNT(o) ,  SUM(o.totalBookCount) ,SUM(o.totalPurchasedAmount)) FROM OrderStatistic o WHERE o.customerId = :customerId GROUP BY o.orderMonth")
    List<MonthlyStatisticDto> monthlyStatistics(@Param("customerId") Long customerId);
}

package com.casestudy.readingisgood.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ORDER_STATISTIC")
@EntityListeners(AuditingEntityListener.class)
public class OrderStatistic  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_statistic_id")
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "order_month")
    private String orderMonth;
    @Column(name = "total_book_count")
    private Long totalBookCount;
    @Column(name = "total_purchased_amount")
    private BigDecimal totalPurchasedAmount;
}

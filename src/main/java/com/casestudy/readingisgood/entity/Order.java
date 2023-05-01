package com.casestudy.readingisgood.entity;

import com.casestudy.readingisgood.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ORDERS")
@EntityListeners(AuditingEntityListener.class)
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_order_customer"))
    private Long customerId;

    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_order_book"))
    private Long bookId;

    @Column(name = "book_count")
    private Long bookCount;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "order_time")
    @CreationTimestamp
    private LocalDateTime orderTime;

    @Column(name = "order_status")
    private OrderStatusEnum orderStatus;


}

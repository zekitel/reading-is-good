package com.casestudy.readingisgood.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
@ToString
@EntityListeners(AuditingEntityListener.class)
@Slf4j
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(unique = true, name = "isbn")
    private String isbn;


    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private Long stock;

    @Version
    @Column(name = "version")
    private int version;



}
package com.casestudy.readingisgood.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOMER")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Customer extends Auditable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(unique = true, name = "email")
    @Email
    @NotBlank
    private String email;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}

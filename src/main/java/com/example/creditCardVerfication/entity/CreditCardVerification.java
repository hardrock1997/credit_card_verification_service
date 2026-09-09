package com.example.creditCardVerfication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "T_CREDIT_CARD_VERIFICATION")
public class CreditCardVerification {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String refId;

    private String firstName;


    private Integer annualIncome;

    private String address;

    private String status;
}

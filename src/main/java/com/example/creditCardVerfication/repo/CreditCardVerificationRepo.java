package com.example.creditCardVerfication.repo;

import com.example.creditCardVerfication.entity.CreditCardVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardVerificationRepo extends JpaRepository<CreditCardVerification,Long> {
}
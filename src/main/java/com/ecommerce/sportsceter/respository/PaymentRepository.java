package com.ecommerce.sportsceter.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sportsceter.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    Payment findByUserEmail(String userEmail);
}

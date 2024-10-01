package com.ecommerce.sportsceter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.sportsceter.model.PaymentResponse;
import com.ecommerce.sportsceter.entity.Payment;
import com.ecommerce.sportsceter.entity.User;
import com.ecommerce.sportsceter.respository.PaymentRepository;
import com.ecommerce.sportsceter.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.secret.key}") String stripeSecretKey, UserService userService) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = stripeSecretKey;  
        this.userService = userService;
    }

    public PaymentIntent createPaymentIntent(PaymentResponse paymentResponse) throws StripeException {
        // set up the payment method types
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        User user = userService.findByUsername(paymentResponse.getUsername());
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", user.getEmail()); 

        Customer customer = Customer.create(customerParams);

        // set up the parameters for the PaymentIntent
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentResponse.getAmount());  
        params.put("currency", paymentResponse.getCurrency());  
        params.put("payment_method_types", paymentMethodTypes);  
        params.put("customer", customer.getId()); 
        // create a PaymentIntent
        return PaymentIntent.create(params);
    }

}


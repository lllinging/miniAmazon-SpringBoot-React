package com.ecommerce.sportsceter.controller;

import com.ecommerce.sportsceter.model.PaymentResponse;
import com.ecommerce.sportsceter.security.JwtAuthenticationFilter;
import com.ecommerce.sportsceter.service.impl.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create PaymentIntent for one-time payment
    @PostMapping("/payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentResponse paymentResponse) throws StripeException {
        // Call your service to create a PaymentIntent
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentResponse);

        Map<String, String> response = new HashMap<>();
        response.put("client_secret", paymentIntent.getClientSecret());
        return ResponseEntity.ok(response);
    }

}

package com.example.payment.service;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String createPaymentUrl(double amount, String upiId){
        String razorpayUrl = "https://razorpay.com/payment-page?upiId="
                +upiId+"&amount="+amount;
        return razorpayUrl;
    }
}

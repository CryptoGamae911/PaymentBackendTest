package com.example.payment.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.Value;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController{
    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @PostMapping("/upi")
    public ResponseEntity<Map<String, String>> createPaymentOrder(@RequestBody Map<String, Object> paymentRequest){
        try{
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

            int amount=(int) paymentRequest.get("amount")*100;

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",amount);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt","txn_123456");
            Order order= razorpayClient.Orders.create(orderRequest);

            Map<String, String> response=new HashMap<>();
            response.put("orderId",order.get("id"));
            response.put("razorpayKey",razorpayKey);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error","Payment initiation failed"));
        }
    }
}

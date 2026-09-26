package com.example.indian_shopping_system.service;

import com.example.indian_shopping_system.dto.PaymentRequest;
import com.example.indian_shopping_system.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    public PaymentResponse processPayment(PaymentRequest request) {
        String method = request.getPaymentMethod() != null ? request.getPaymentMethod().toUpperCase() : "COD";
        String txnId = "TXN_IND_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        switch (method) {
            case "UPI":
                String upi = request.getUpiId() != null && !request.getUpiId().isBlank() ? request.getUpiId() : "user@upi";
                return new PaymentResponse(
                    txnId,
                    "SUCCESS",
                    "UPI (" + upi + ")",
                    "Payment of ₹" + request.getAmount() + " processed successfully via UPI."
                );

            case "CARD":
                return new PaymentResponse(
                    txnId,
                    "SUCCESS",
                    "CARD (RuPay/Debit/Credit)",
                    "Card payment of ₹" + request.getAmount() + " authorized successfully."
                );

            case "NET_BANKING":
                return new PaymentResponse(
                    txnId,
                    "SUCCESS",
                    "NET_BANKING",
                    "NetBanking payment of ₹" + request.getAmount() + " completed successfully."
                );

            case "COD":
            default:
                return new PaymentResponse(
                    "COD_" + System.currentTimeMillis(),
                    "PENDING",
                    "CASH_ON_DELIVERY",
                    "Cash on Delivery selected. Pay ₹" + request.getAmount() + " on parcel arrival."
                );
        }
    }
}

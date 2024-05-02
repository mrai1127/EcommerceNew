package com.rai.demo.controller;

import com.rai.demo.dto.checkout.CheckoutItemDto;
import com.rai.demo.dto.checkout.StripeResponse;
import com.rai.demo.service.AuthenticationService;
import com.rai.demo.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    //stripe session checkout api
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse>checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtos)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemDtos);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

}

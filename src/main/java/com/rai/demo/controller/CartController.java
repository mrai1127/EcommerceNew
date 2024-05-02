package com.rai.demo.controller;

import com.rai.demo.common.ApiResponse;
import com.rai.demo.dto.cart.AddToCartDto;
import com.rai.demo.dto.cart.CartDto;
import com.rai.demo.model.User;
import com.rai.demo.service.AuthenticationService;
import com.rai.demo.service.CartService;
import com.rai.demo.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProductService productService;

    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){

        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }
    //get all cart items for user api

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        //get cart items
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    //delete cart item for user api
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable ("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token){

        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId,user);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been deleted"), HttpStatus.OK);

    }
}

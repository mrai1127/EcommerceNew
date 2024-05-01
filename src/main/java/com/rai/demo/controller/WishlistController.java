package com.rai.demo.controller;

import com.rai.demo.common.ApiResponse;
import com.rai.demo.dto.ProductDto;
import com.rai.demo.model.Product;
import com.rai.demo.model.User;
import com.rai.demo.model.WishList;
import com.rai.demo.repository.WishListRepository;
import com.rai.demo.service.AuthenticationService;
import com.rai.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;


    @Autowired
    AuthenticationService authenticationService;

    //save product to wishlist
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product,
                                                     @RequestParam("token") String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        //save the item in wishlist
        WishList wishList = new WishList(user, product);
        wishlistService.createWishList(wishList);
        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    //get all wishlist item for user
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishlistService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}

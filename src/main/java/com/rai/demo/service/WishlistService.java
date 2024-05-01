package com.rai.demo.service;

import com.rai.demo.dto.ProductDto;
import com.rai.demo.model.User;
import com.rai.demo.model.WishList;
import com.rai.demo.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
       final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
       List<ProductDto> productDtos = new ArrayList<>();
       for(WishList wishList: wishLists){
           productDtos.add(productService.getProductDto(wishList.getProduct()));
       }
       return productDtos;
    }
}

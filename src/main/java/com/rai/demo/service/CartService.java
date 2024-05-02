package com.rai.demo.service;

import com.rai.demo.dto.cart.AddToCartDto;
import com.rai.demo.dto.cart.CartDto;
import com.rai.demo.dto.cart.CartItemDto;
import com.rai.demo.exceptions.CustomException;
import com.rai.demo.model.Cart;
import com.rai.demo.model.Product;
import com.rai.demo.model.User;
import com.rai.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;
    public void addToCart(AddToCartDto addToCartDto, User user) {
        //validate if the product id is valid
       Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        //save the cart
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
       List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

       List<CartItemDto> cartItems = new ArrayList<>();
       double totalCost = 0;
       for(Cart cart : cartList){
           CartItemDto cartItemDto = new CartItemDto(cart);
           cartItems.add(cartItemDto);
           totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
       }
       CartDto cartDto = new CartDto();
       cartDto.setCartItems(cartItems);
        cartDto.setTotalCost(totalCost);
       return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {

        //the item id should belong to user
        Optional<Cart>optionalCart = cartRepository.findById(cartItemId);
        if(optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if(cart.getUser() != user){
            throw new CustomException("cart item dows not belong to user: " + cartItemId);
        }
        cartRepository.delete(cart);
    }
}

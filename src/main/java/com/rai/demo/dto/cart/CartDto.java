package com.rai.demo.dto.cart;

import java.util.List;

public class CartDto {

    List<CartItemDto> CartItems;
    private double totalCost;

    public CartDto() {
    }

    public List<CartItemDto> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        CartItems = cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public CartDto(List<CartItemDto> cartItems, double totalCost) {

        CartItems = cartItems;
        this.totalCost = totalCost;
    }
}

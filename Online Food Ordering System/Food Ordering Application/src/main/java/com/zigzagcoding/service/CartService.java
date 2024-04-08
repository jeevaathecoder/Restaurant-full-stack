package com.zigzagcoding.service;

import com.zigzagcoding.model.Cart;
import com.zigzagcoding.model.CartItem;
import com.zigzagcoding.model.User;
import com.zigzagcoding.request.AddCartItemRequest;

public interface CartService {

   public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception;

   public  CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

   public Cart removeCartItem(Long cartItemId , String jwt) throws Exception;

   public Long calucateCartTotals(Cart cart) throws Exception;

   public Cart findCartById(Long id) throws Exception;

   public Cart findCartByUserId(Long userId) throws Exception;

   public Cart clearCart(Long  userId) throws Exception;
}

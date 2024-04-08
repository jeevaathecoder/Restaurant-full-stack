package com.zigzagcoding.request;

import lombok.Data;

@Data
public class IngredietnItemRequest {
    
    private String name;
    private Long categoryId;
    private Long restaurantId;
}


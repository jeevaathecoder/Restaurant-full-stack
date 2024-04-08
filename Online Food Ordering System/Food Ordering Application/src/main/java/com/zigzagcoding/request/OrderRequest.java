package com.zigzagcoding.request;

import com.zigzagcoding.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}

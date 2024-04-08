package com.zigzagcoding.repository;

import com.zigzagcoding.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepostiory  extends JpaRepository<OrderItem, Long>{

}

package com.zigzagcoding.repository;

import com.zigzagcoding.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {

    public List<com.zigzagcoding.model.Category> findByRestaurantId(Long id);
}

package com.zigzagcoding.repository;

import com.zigzagcoding.model.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredinetsCategoryRepository  extends JpaRepository<IngredientsCategory, Long>{

    List<IngredientsCategory> findByRestaurantId(Long id);

}

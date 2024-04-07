package com.zigzagcoding.repository;

import com.zigzagcoding.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :searchQuery, '%')) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :searchQuery, '%'))")
    public List<Restaurant> findBySearchQuery(String searchQuery);

    public Restaurant findByOwnerId(Long userId);

}

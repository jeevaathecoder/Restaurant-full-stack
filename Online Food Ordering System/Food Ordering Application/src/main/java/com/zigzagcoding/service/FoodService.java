package com.zigzagcoding.service;

import com.zigzagcoding.model.Category;
import com.zigzagcoding.model.Food;
import com.zigzagcoding.model.IngredientsItem;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void delteFood(Long id) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long id) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;


}

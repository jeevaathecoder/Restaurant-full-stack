package com.zigzagcoding.service;


import com.zigzagcoding.model.IngredientsCategory;
import com.zigzagcoding.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredient(String ingredientName, Long restaurantId, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateStock (Long id) throws Exception;


}

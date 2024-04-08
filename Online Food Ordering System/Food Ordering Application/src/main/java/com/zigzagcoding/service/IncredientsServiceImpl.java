package com.zigzagcoding.service;

import com.zigzagcoding.model.IngredientsCategory;
import com.zigzagcoding.model.IngredientsItem;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.repository.IngredientsItemRepository;
import com.zigzagcoding.repository.IngredinetsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncredientsServiceImpl implements IngredientsService{


    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private IngredinetsCategoryRepository ingredinetsCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;




    @Override
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory = new IngredientsCategory();
        ingredientsCategory.setName(name);
        ingredientsCategory.setRestaurant(restaurant);

        IngredientsCategory savedCategory = ingredinetsCategoryRepository.save(ingredientsCategory);
        return savedCategory;

    }



    @Override
    public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientsCategory> category = ingredinetsCategoryRepository.findById(id);
        if(category.isEmpty()){
           throw new Exception("Category not found");
        }

        return category.get();
    }


    @Override
    public List<IngredientsCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredinetsCategoryRepository.findByRestaurantId(id);
    }


    @Override
    public IngredientsItem createIngredient(String ingredientName, Long restaurantId, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category = findIngredientCategoryById(categoryId);
        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(category);

        return null;
    }


    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return  ingredientsItemRepository.findByRestaurantId(restaurantId);

    }


    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem = ingredientsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Item not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();

        ingredientsItem.setInStock(!ingredientsItem.isInStock());

        return ingredientsItemRepository.save(ingredientsItem);
    }
}

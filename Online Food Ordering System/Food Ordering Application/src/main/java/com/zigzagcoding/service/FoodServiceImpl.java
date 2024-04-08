package com.zigzagcoding.service;

import com.zigzagcoding.model.Category;
import com.zigzagcoding.model.Food;
import com.zigzagcoding.model.IngredientsItem;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.repository.FoodRepository;
import com.zigzagcoding.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {

        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setSeasonal(request.isSeasional());
        food.setVegetarian(request.isVegetarin());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;

    }

    @Override
    public void delteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods = filterByVegetarian(foods,isVegetarian);
        }
        if(isNonveg){
            foods = filterByNonveg(foods,isNonveg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory != null && !foodCategory.isEmpty()){
        foods = filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food = foodRepository.findById(id);
        if(food.isEmpty()){
            throw new Exception("Food not found");

        }
        return food.get();
    }


    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailale(!food.isAvailale());
        return foodRepository.save(food);

    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equalsIgnoreCase(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());

    }


}


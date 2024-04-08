package com.zigzagcoding.controller;

import com.zigzagcoding.model.Food;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.model.User;
import com.zigzagcoding.request.CreateFoodRequest;
import com.zigzagcoding.response.MessageResponse;
import com.zigzagcoding.service.FoodService;
import com.zigzagcoding.service.RestaurantService;
import com.zigzagcoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
                                                       @RequestParam boolean seasonal,
                                                       @RequestParam boolean nonVeg,
                                                       @RequestParam(required = false) String foodCategory,
                                                       @PathVariable Long restaurantId,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantFood(restaurantId,vegetarian,nonVeg,seasonal,foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}

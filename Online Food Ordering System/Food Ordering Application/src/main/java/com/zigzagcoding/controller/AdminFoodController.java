package com.zigzagcoding.controller;

import com.zigzagcoding.model.Food;
import com.zigzagcoding.model.IngredientsItem;
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

@RestController
@RequestMapping("api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood( @RequestBody CreateFoodRequest req,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());
        Food food = foodService.createFood(req, req.getCategory(),restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.delteFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailiblityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        MessageResponse messageResponse = new MessageResponse();

        return new ResponseEntity<>(food, HttpStatus.OK);
    }



}

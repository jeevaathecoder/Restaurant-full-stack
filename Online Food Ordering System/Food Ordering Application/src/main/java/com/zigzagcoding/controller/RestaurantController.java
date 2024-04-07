package com.zigzagcoding.controller;

import com.zigzagcoding.dto.RestaurantDto;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.model.User;
import com.zigzagcoding.request.CreateRestaurantRequest;
import com.zigzagcoding.service.RestaurantService;
import com.zigzagcoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
          @RequestHeader("Authorization") String jwt,
          @RequestParam("keyword") String keyword
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant , HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurant , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("id") Long id

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant , HttpStatus.OK);
    }


    @PutMapping("/{id}/add-favourite")
    public ResponseEntity<RestaurantDto> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("id") Long id

    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto dto = restaurantService.addToFavourites(id, user);
        return new ResponseEntity<>(dto , HttpStatus.OK);
    }



}

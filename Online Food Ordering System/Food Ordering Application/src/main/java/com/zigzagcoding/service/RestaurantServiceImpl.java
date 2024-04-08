package com.zigzagcoding.service;

import com.zigzagcoding.dto.RestaurantDto;
import com.zigzagcoding.model.Address;
import com.zigzagcoding.model.Restaurant;
import com.zigzagcoding.model.User;
import com.zigzagcoding.repository.AddressRepository;
import com.zigzagcoding.repository.RestaurantRepository;
import com.zigzagcoding.repository.UserRepository;
import com.zigzagcoding.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = req.getAddress();
        addressRepository.save(address);

       Restaurant restaurant = new Restaurant();
       restaurant.setAddress(address);
       restaurant.setContactInformation(req.getContactInformation());
       restaurant.setCuisineType(req.getCuisineType());
       restaurant.setDescription(req.getDescription());
       restaurant.setImages(req.getImages());
       restaurant.setName(req.getName());
       restaurant.setOpeningHours(req.getOpeningHours());
       restaurant.setRegistrationDate(LocalDateTime.now());
       restaurant.setOwner(user);

       restaurantRepository.save(restaurant);

        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new Exception("Restaurant not found"));
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if(restaurant.getImages()!=null){
            restaurant.setImages(updatedRestaurant.getImages());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        if(restaurant.getOpeningHours()!=null){
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        if(restaurant.getContactInformation()!=null){
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }
        if(restaurant.getAddress()!=null){
            restaurant.setAddress(updatedRestaurant.getAddress());
        }
        restaurantRepository.save(restaurant);
        return restaurant;

    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
       Restaurant restaurant = findRestaurantById(restaurantId);
       restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(opt.isEmpty()){
           throw new Exception("Restaurant not found");
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if(restaurant == null){
            throw new Exception("Restaurant not found with owener id: "+userId);

        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto  dto= new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurant.getId());

        boolean isFavorited = false;

        List<RestaurantDto> favourites = user.getFavourties();
        for(RestaurantDto favourite : favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavorited = true;
                break;
            }
        }
       if(isFavorited){
           favourites.removeIf(favourite -> favourite.getId().equals(restaurantId));
       }
        else {
            favourites.add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}

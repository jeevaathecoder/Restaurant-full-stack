package com.zigzagcoding.controller;

import com.zigzagcoding.model.IngredientsCategory;
import com.zigzagcoding.model.IngredientsItem;
import com.zigzagcoding.request.IngredientCategoryRequest;
import com.zigzagcoding.request.IngredietnItemRequest;
import com.zigzagcoding.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private UserController userController;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req)
            throws Exception {
        IngredientsCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }



    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredietnItemRequest req)
            throws Exception {
        IngredientsItem item = ingredientsService.createIngredient(req.getName(), req.getRestaurantId(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id)
            throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id)
            throws Exception {
        List<IngredientsItem> item = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id)
            throws Exception {
        List<IngredientsCategory> item = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


}

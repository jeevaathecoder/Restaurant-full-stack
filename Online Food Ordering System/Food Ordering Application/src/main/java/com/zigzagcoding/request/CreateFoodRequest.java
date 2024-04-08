package com.zigzagcoding.request;

import com.zigzagcoding.model.Category;
import com.zigzagcoding.model.IngredientsItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;

    private List<String> images;

    private Long restaurantId;


    private boolean vegetarin;
    private boolean seasional;

    private List<IngredientsItem> ingredients;


}

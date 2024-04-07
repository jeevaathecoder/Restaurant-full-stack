package com.zigzagcoding.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IngredientsCategory {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String name;

        @ManyToOne
        @JsonIgnore
        private Restaurant restaurant;

        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<IngredientsItem> items = new ArrayList<>();
}

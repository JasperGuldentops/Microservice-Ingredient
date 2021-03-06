package com.jasperg.ingredient.repository;

import com.jasperg.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findAll();
    List<Ingredient> findIngredientsByRecipeCode(String code);
    List<Ingredient> findIngredientsByNameContaining(String name);
    Ingredient findIngredientByCode(String code);
}

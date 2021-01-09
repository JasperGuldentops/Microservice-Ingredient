package com.jasperg.ingredient.controller;

import com.jasperg.ingredient.model.Ingredient;
import com.jasperg.ingredient.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

//    @PostConstruct
//    public void fillDB() {
//        if(ingredientRepository.count() == 0) {
//            ingredientRepository.save(new Ingredient("Tomato", 2, "Pizza0000", "0000"));
//            ingredientRepository.save(new Ingredient("Cheese", 150, "Pizza0000", "0000"));
//            ingredientRepository.save(new Ingredient("Flour", 500, "Pizza0000", "0000"));
//            ingredientRepository.save(new Ingredient("Egg", 2, "Pizza0000", "0000"));
//            ingredientRepository.save(new Ingredient("Water", 200, "Pizza0000", "0000"));
//        }
//    }

    @GetMapping("/ingredients")
    public List<Ingredient> getIngredients() {

        return ingredientRepository.findAll();
    }

    @GetMapping("/ingredients/recipeCode/{recipeCode}")
    public List<Ingredient> getIngredientsByRecipeCode(@PathVariable String recipeCode) {

        return ingredientRepository.findIngredientsByRecipeCode(recipeCode);
    }

    @GetMapping("/ingredients/name/{name}")
    public List<Ingredient> getIngredientsByName(@PathVariable String name) {

        return ingredientRepository.findIngredientsByNameContaining(name);
    }

    @GetMapping("/ingredients/code/{code}")
    public Ingredient getIngredientByCode(@PathVariable String code) {

        return ingredientRepository.findIngredientByCode(code);
    }

    @PostMapping("/ingredients")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient){

        Ingredient newIngredient = new Ingredient(ingredient.getName(), ingredient.getAmount(),
                ingredient.getRecipeCode());

        ingredientRepository.save(newIngredient);

        return newIngredient;
    }

    @PutMapping("/ingredients")
    public Ingredient updateIngredient(@RequestBody Ingredient updatedIngredient){

        Ingredient ingredient = ingredientRepository.findIngredientByCode(updatedIngredient.getCode());

        ingredient.setName(updatedIngredient.getName());
        ingredient.setAmount(updatedIngredient.getAmount());

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    @DeleteMapping("/ingredients/{code}")
    public ResponseEntity deleteUser(@PathVariable String code){

        Ingredient ingredient = ingredientRepository.findIngredientByCode(code);
        if(ingredient!=null){
            ingredientRepository.delete(ingredient);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}

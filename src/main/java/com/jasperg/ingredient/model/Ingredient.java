package com.jasperg.ingredient.model;

import com.jasperg.ingredient.helper.Helper;

import javax.persistence.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int amount;

    private String recipeCode;

    private String code;

    public Ingredient() {
    }

    public Ingredient(String name, int amount, String recipeCode) {
        this.name = name;
        this.amount = amount;
        this.recipeCode = recipeCode;
        this.code = recipeCode + "-" + Helper.getRandomString(4);
    }

    public Ingredient(String name, int amount, String recipeCode, String codeString) {
        this.name = name;
        this.amount = amount;
        this.recipeCode = recipeCode;
        this.code = recipeCode + "-" + codeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRecipeCode() {
        return recipeCode;
    }

    public void setRecipeCode(String recipeCode) {
        this.recipeCode = recipeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

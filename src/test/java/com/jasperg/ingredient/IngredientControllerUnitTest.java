package com.jasperg.ingredient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasperg.ingredient.model.Ingredient;
import com.jasperg.ingredient.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenIngredient_whenGetAllIngredients_thenReturnJsonIngredients() throws Exception {

        Ingredient ingredient1 = new Ingredient("Tomato", 2, "Pizza-0000", "0000");
        Ingredient ingredient2 = new Ingredient("Water", 250, "Pizza-0000", "0001");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        given(ingredientRepository.findAll()).willReturn(ingredients);

        mockMvc.perform(get("/ingredients"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].name", is("Tomato")))
                .andExpect(jsonPath("$[0].amount", is(2)))
                .andExpect(jsonPath("$[0].recipeCode", is("Pizza-0000")))
                .andExpect(jsonPath("$[0].code", is("Pizza-0000-0000")))

                .andExpect(jsonPath("$[1].name", is("Water")))
                .andExpect(jsonPath("$[1].amount", is(250)))
                .andExpect(jsonPath("$[1].recipeCode", is("Pizza-0000")))
                .andExpect(jsonPath("$[1].code", is("Pizza-0000-0001")));
    }

    @Test
    public void givenIngredient_whenGetIngredientsByRecipeCode_thenReturnJsonIngredients() throws Exception {

        Ingredient ingredient1 = new Ingredient("Milk", 200, "Milkshake-0000", "0000");
        Ingredient ingredient2 = new Ingredient("Sugar", 50, "Milkshake-0000", "0001");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        given(ingredientRepository.findIngredientsByRecipeCode("Milkshake-0000")).willReturn(ingredients);

        mockMvc.perform(get("/ingredients/recipeCode/{recipeCode}", "Milkshake-0000"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].name", is("Milk")))
                .andExpect(jsonPath("$[0].amount", is(200)))
                .andExpect(jsonPath("$[0].recipeCode", is("Milkshake-0000")))
                .andExpect(jsonPath("$[0].code", is("Milkshake-0000-0000")))

                .andExpect(jsonPath("$[1].name", is("Sugar")))
                .andExpect(jsonPath("$[1].amount", is(50)))
                .andExpect(jsonPath("$[1].recipeCode", is("Milkshake-0000")))
                .andExpect(jsonPath("$[1].code", is("Milkshake-0000-0001")));
    }

    @Test
    public void givenIngredient_whenGetIngredientsByNameContaining_thenReturnJsonIngredients() throws Exception {

        Ingredient ingredient1 = new Ingredient("Tomato", 2, "Pizza-0000", "0000");
        Ingredient ingredient2 = new Ingredient("Water", 250, "Pizza-0000", "0001");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        given(ingredientRepository.findIngredientsByNameContaining("at")).willReturn(ingredients);

        mockMvc.perform(get("/ingredients/name/{name}", "at"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].name", is("Tomato")))
                .andExpect(jsonPath("$[0].amount", is(2)))
                .andExpect(jsonPath("$[0].recipeCode", is("Pizza-0000")))
                .andExpect(jsonPath("$[0].code", is("Pizza-0000-0000")))

                .andExpect(jsonPath("$[1].name", is("Water")))
                .andExpect(jsonPath("$[1].amount", is(250)))
                .andExpect(jsonPath("$[1].recipeCode", is("Pizza-0000")))
                .andExpect(jsonPath("$[1].code", is("Pizza-0000-0001")));
    }

    @Test
    public void givenIngredient_whenGetIngredientByCode_thenReturnJsonIngredient() throws Exception {

        Ingredient ingredient = new Ingredient("Tomato", 2, "Pizza-0000", "0000");

        given(ingredientRepository.findIngredientByCode("Pizza-0000-0000")).willReturn(ingredient);

        mockMvc.perform(get("/ingredients/code/{code}", "Pizza-0000-0000"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Tomato")))
                .andExpect(jsonPath("$.amount", is(2)))
                .andExpect(jsonPath("$.recipeCode", is("Pizza-0000")))
                .andExpect(jsonPath("$.code", is("Pizza-0000-0000")));
    }

    @Test
    public void whenPostIngredient_thenReturnJsonIngredient() throws Exception {

        Ingredient newIngredient = new Ingredient("Ice cream", 200, "Milkshake-0000", "0002");

        mockMvc.perform(post("/ingredients")
                .content(mapper.writeValueAsString(newIngredient))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Ice cream")))
                .andExpect(jsonPath("$.amount", is(200)))
                .andExpect(jsonPath("$.recipeCode", is("Milkshake-0000")))
                .andExpect(jsonPath("$.code", is("Milkshake-0000-0002")));
    }

    @Test
    public void whenPutIngredient_thenReturnJsonIngredient() throws Exception {

        Ingredient ingredient = new Ingredient("Saltz", 1, "Omelette-0000", "0001");

        given(ingredientRepository.findIngredientByCode("Omelette-0000-0001")).willReturn(ingredient);

        Ingredient updateIngredient = new Ingredient("Salt", 3, "Omelette-0000", "0001");

        mockMvc.perform(put("/ingredients")
                .content(mapper.writeValueAsString(updateIngredient))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Salt")))
                .andExpect(jsonPath("$.amount", is(3)))
                .andExpect(jsonPath("$.recipeCode", is("Omelette-0000")))
                .andExpect(jsonPath("$.code", is("Omelette-0000-0001")));
    }

    @Test
    public void givenIngredient_whenDeleteIngredient_thenStatusOk() throws Exception {

        Ingredient ingredient = new Ingredient("Ice cream", 200, "Milkshake-0000", "0002");

        given(ingredientRepository.findIngredientByCode("Milkshake-0000-0002")).willReturn(ingredient);

        mockMvc.perform(delete("/ingredients/{code}", "Milkshake-0000-0002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenIngredient_whenDeleteIngredient_thenStatusNotFound() throws Exception {

        given(ingredientRepository.findIngredientByCode("Not a real code")).willReturn(null);

        mockMvc.perform(delete("/ingredients/{code}", "Not a real code")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

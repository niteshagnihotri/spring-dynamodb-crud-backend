package com.personal.recipebackend.controller;


import com.personal.recipebackend.model.Recipe;
import com.personal.recipebackend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/")
    public String Home(){
        return "Server running";
    }
    @PostMapping("/recipes")
    public Recipe saveRecipe(@RequestBody Recipe recipe){
        return recipeRepository.save(recipe);
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipeById(@PathVariable("id") String recipeId){
        return recipeRepository.getRecipeById(recipeId);
    }

    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable("id") String recipeId){
        return recipeRepository.delete(recipeId);
    }

    @PutMapping("/recipes/{id}")
    public String updateEmployee(@PathVariable("id") String recipeId, @RequestBody Recipe recipe){
        return recipeRepository.update(recipeId, recipe);
    }
}

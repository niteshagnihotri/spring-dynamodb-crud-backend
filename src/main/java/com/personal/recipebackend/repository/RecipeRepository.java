package com.personal.recipebackend.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.personal.recipebackend.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Recipe save(Recipe recipe){
        dynamoDBMapper.save(recipe);
        return recipe;
    }

    public Recipe getRecipeById(String recipeId){
        return dynamoDBMapper.load(Recipe.class, recipeId);
    }

    public String delete(String recipeId){
        Recipe recipe = dynamoDBMapper.load(Recipe.class, recipeId);
        dynamoDBMapper.delete(recipe);
        return "Recipe Deleted";
    }

    public String update(String recipeId, Recipe recipe) {
        // Set the recipeId in the object to ensure consistency
        recipe.setRecipeId(recipeId);

        // Check if the item exists before updating
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
                .withExpectedEntry("recipeId",
                        new ExpectedAttributeValue(new AttributeValue().withS(recipeId)));

        // Perform the update
        try {
            dynamoDBMapper.save(recipe, saveExpression);
            return recipeId;
        } catch (ConditionalCheckFailedException e) {
            // Handle case where item does not exist or condition fails
            System.out.println("Error: Recipe with ID " + recipeId + " does not exist or condition failed.");
            throw e;
        }
    }

}

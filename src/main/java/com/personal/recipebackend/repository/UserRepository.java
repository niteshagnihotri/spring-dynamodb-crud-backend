package com.personal.recipebackend.repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.personal.recipebackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public User save(User user){
        dynamoDBMapper.save(user);
        return user;
    }

    public User loadUserByUserName(String userName){
       try{
           DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                   .withIndexName("UsernameIndex")  // The name of the GSI
                   .withConsistentRead(false)       // GSI does not support consistent reads
                   .withKeyConditionExpression("userName = :v_userName")
                   .withExpressionAttributeValues(Map.of(":v_userName", new AttributeValue().withS(userName)));
           // Query the table using the GSI
           List<User> result = dynamoDBMapper.query(User.class, queryExpression);
           // Assuming username is unique, return the first result if exists
           return result.isEmpty() ? null : result.getFirst();
       }
       catch(Exception e){
           System.out.println("error "+e.getMessage());
       }
       return null;
    }
}

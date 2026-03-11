# SaltAndPepper

## Author
Jennifer Burkmier
3/11/2026 - present

## Description: 
Salt and Pepper is an ongoing project that demonstrates knowledge of skills listed in "Skills Demonstrated" section. This is a personal side project to apply what I am learning in university but is not a school project. 

This project is built using Java and Gradle and uses SQL scripts to create and populate the database. 

Features will be added and changed routinely. Use the "main" branch for the most up-to-date version but the "InProgress" branch can be used to see what new features or updates I am currently working on. 

## Requirements
- Java JDK 17 or newer
- Gradle or use the included Gradle Wrapper



## How to Run: 
    Database Setup: 
        Run SQL scripts in the following order:
            - Create.sql
            - Insert.sql
   
    gradle build (or ./gradlew build)
    gradle run
    
   Connect to database: 
   URL: jdbc:mysql://localhost:3306/SaltAndPepper
   Username: MYSQL username
   Password: MYSQL password

## Current Features: 
- View all recipes in Recipe Book
- Search recipes and view card by name from Recipe Book
- Add new recipes to Recipe Book
- View Meal Plan example functionality

## Skills Demonstrated
- SQL Database Management
- JAVA OOP
- Using GIT locally to push to GITHUB

## Ideas for Expansion: 
- Sort answers by ingredient amounts
- Suggest meals based on what is currently on stock in pantry 
- Meal plan for each day of week for breakfast, lunch, dinner
- Build grocery list from meal plan
- Edit meals into database
- Log in feature for different accounts
- Build Unit Tests
- Javadoc documentation
- Expansion to a more interactive GUI (JavaFX or HTML)
- Measurement converter

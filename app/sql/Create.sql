CREATE DATABASE SaltAndPepper;

USE SaltAndPepper; 

DROP DATABASE IF EXISTS saltandpepper;
CREATE DATABASE saltandpepper;
USE saltandpepper;

CREATE TABLE RECIPE(
    Meal_Name VARCHAR(50) PRIMARY KEY, 
    Measurements VARCHAR (1500),
    Ingredients VARCHAR(200),
    Instructions VARCHAR(1500)
);


package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CookBook {

    private final Connection connection;
    private final Scanner scanner;

    public CookBook(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run() {
        while (true){
        System.out.println("\n+++++ Cook Book +++++");
        System.out.println("1. View All Recipes");
        System.out.println("2. View Recipe Page");
        System.out.println("3. Add New Recipe");
        System.out.println("4. Edit Recipe");
        System.out.println("5. Exit to Main Menu");


        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 5) {
            System.out.println("Closing Cook Book...");
            return;
        }
        browseRecipes(choice);
        }
    }

    public void browseRecipes(int choice) {
            switch (choice) {
                case 1 -> {
                    viewAll();
                    break;
                }
                case 2 -> {
                    viewPage();
                    break;
                }
                case 3 -> {
                    addRecipe();
                    break;
                }
                case 4 -> {
                    editRecipe();
                    break;
                }
            }
        }
        
    /**
     * Displays all recipes in the Recipe Book
     */
    public void viewAll(){               
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
        System.out.println("\nRecipes: ");
        ps = connection.prepareStatement("""
            SELECT Recipe.Meal_Name
            FROM RECIPE
            """);
        rs = ps.executeQuery();
        while (rs.next()) {
        System.out.println(rs.getString("Meal_name"));
        }
        }catch (SQLException e){
            System.out.println("Error loading recipes: " + e.getMessage());          
        } finally {
            try { 
                if (rs != null){
                    rs.close();
                }
                if (ps != null){
                    ps.close();
                }
            } catch (SQLException e){
                System.out.println("Failed to close: " + e.getMessage());
            }
        }
        
    }

    /**
     * Displays recipe page of selected recipe
     */
    public void viewPage(){

        String recipe; 
        System.out.println("\nEnter name of recipe: ");
        recipe = scanner.nextLine();

        displayRecipe(recipe);

    }

    public void displayRecipe(String recipeName){
        PreparedStatement ps = null;
        ResultSet rs = null; 

        try{
        ps = connection.prepareStatement("""
            SELECT Meal_name, Measurements, Ingredients, Instructions
            FROM RECIPE
            WHERE Meal_name = ?
            """);

        ps.setString(1, recipeName);
        rs = ps.executeQuery();

        if (rs.next()){
            System.out.println("\n+++++" + rs.getString("Meal_name") + "+++++");

            String[] ingredients = rs.getString("Ingredients").split("\n");
            String[] measurements = rs.getString("Measurements").split("\n");

            System.out.println("\nIngredients: ");
            for (int i = 0; i < ingredients.length; i++){
                String measurement = ""; 
                if (i < measurements.length){
                    measurement = measurements[i];
                }
                System.out.printf("  %-20s %s\n", ingredients[i], measurement);
            }
            System.out.println("\nInstructions: \n" + rs.getString("Instructions"));
        } else {
            System.out.println("Recipe not found");
        }

        }catch (SQLException e){
            System.out.println("Error loading recipes: " + e.getMessage());          
        } finally {
            try { 
                if (rs != null){
                    rs.close();
                }
                if (ps != null){
                    ps.close();
                }
            } catch (SQLException e){
                System.out.println("Failed to close: " + e.getMessage());
            }
        }
    }

    /**
     * Adds recipe information to Recipe Book
     */
    public void addRecipe(){
        PreparedStatement ps = null;

        System.out.println("\nEnter Name of Recipe: ");
        String recipeName = scanner.nextLine();
        System.out.println("\nEnter Measurements for Ingredients and type 'done' when finished");
        String addMeasurements = (buildString()).toString();
        System.out.println("\nEnter Ingredients");
        String addIngredients = (buildString()).toString();
        System.out.println("\nEnter Instructions");
        String addInstructions = (buildString()).toString();

        try{
        ps = connection.prepareStatement("""
            INSERT INTO RECIPE (Meal_name, Measurements, Ingredients, Instructions)
            VALUES (?, ?, ?, ?)
            """);

        ps.setString(1, recipeName);
        ps.setString(2, addMeasurements);
        ps.setString(3, addIngredients);
        ps.setString(4, addInstructions);
        ps.executeUpdate();

        System.out.println("Recipe added successfully!");

        }catch (SQLException e){
            System.out.println("Error loading recipes: " + e.getMessage());          
        } finally {
            try { 
                if (ps != null){
                    ps.close();
                }
            } catch (SQLException e){
                System.out.println("Failed to close: " + e.getMessage());
            }
        }
    }

    /**
     * Helper method to insert information into Recipe 
     */
    public StringBuilder buildString(){
        StringBuilder builder = new StringBuilder();
            while(true){
                String line = scanner.nextLine();
                if (line.equals("done")){
                    break;
                }
            builder.append(line).append("\n");
            }
            return builder;
        }

    public void editRecipe(){
        System.out.println("\nEnter Recipe to Edit:");
        String recipeName = scanner.nextLine();

        displayRecipe(recipeName);

        System.out.println("\nWhat do you want to edit?");
        System.out.println("1. Recipe Name");
        System.out.println("2. Measurements");
        System.out.println("3. Ingredients");
        System.out.println("4. Instructions");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String columnName = null;
        String prompt = null;

        switch(choice){
            case 1 -> {
                columnName = "Meal_Name";
                prompt = "Enter new recipe name:";
            }
            case 2 -> {
                columnName = "Measurements"; 
                prompt = "Enter new measurements";
            }
            case 3 -> {
                columnName = "Ingredients";
                prompt = "Enter new ingredients";
            }
            case 4 -> {
                columnName = "Instructions";
                prompt = "Enter new instructions";
            }
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }
        System.out.println(prompt);
        String newValue = scanner.nextLine();
        updateRecipeField(columnName, newValue, recipeName);
    }

    public void updateRecipeField(String columnName, String newValue, String recipeName){
        String sql = "UPDATE RECIPE SET " + columnName + " = ? WHERE Meal_name = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, newValue);
            ps.setString(2, recipeName);

            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated > 0){
                System.out.println("Recipe updated successfully.");
            } else {
                System.out.println("Recipe not found.");
            }
        } catch (SQLException e){
            System.out.println("Error updating recipe.");
            e.printStackTrace();
        }
    }

    }

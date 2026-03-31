package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Interacts with the database of recipes
 */
public class CookBook {

    private final Connection connection;
    private final Scanner scanner;

    /**
     * Constructor for CookBook class
     * @param scanner Scanner keyboard scanner
     * @param connection Connection that connects to database
     */
    public CookBook(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    /**
     * Displays options for interacting with Cook Book
     */
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

    /**
     * Handles choice from run() method
     * @param choice int choice selection from run() 
     */
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
     * Allows users to enter recipe name to view the recipe page
     */
    public void viewPage(){

        String recipe; 
        System.out.println("\nEnter name of recipe: ");
        recipe = scanner.nextLine();

        displayRecipe(recipe);

    }

    /**
     * Displays recipe page
     * @param recipeName Name of recipe to display
     */
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
        System.out.println("\nEnter Category: ");
        String categoryName = scanner.nextLine();
        System.out.println("\nEnter Measurements for Ingredients and type 'done' when finished");
        String addMeasurements = (buildString()).toString();
        System.out.println("\nEnter Ingredients and type 'done' when finished");
        String addIngredients = (buildString()).toString();
        System.out.println("\nEnter Instructions");
        String addInstructions = (buildString()).toString();

        try{
        ps = connection.prepareStatement("""
            INSERT INTO RECIPE (Meal_name, Measurements, Ingredients, Instructions)
            VALUES (?, ?, ?, ?)
            """);

        ps.setString(1, recipeName);
        ps.setString(2, categoryName);
        ps.setString(3, addMeasurements);
        ps.setString(4, addIngredients);
        ps.setString(5, addInstructions);
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
     * @return builder 
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

    /**
     * Edits recipe page
     */
    public void editRecipe(){
        System.out.println("\nEnter Recipe to Edit:");
        String recipeName = scanner.nextLine();

        displayRecipe(recipeName);

        while (true) {
            System.out.println("\nWhat do you want to edit?");
            System.out.println("1. Recipe Name");
            System.out.println("2. Category");
            System.out.println("3. Measurements");
            System.out.println("4. Ingredients");
            System.out.println("5. Instructions");
            System.out.println("6. Done Editing");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 6) {
                System.out.println("Finished editing.");
                return;
            }

            String columnName = null;
            String newValue = null;

            switch(choice){
                case 1 -> {
                    columnName = "Meal_Name";
                    System.out.println("Enter new recipe name:");
                    newValue = scanner.nextLine();
                }
                case 2 -> {
                    columnName = "Category";
                    System.out.println("Enter new category:");
                    newValue = scanner.nextLine();
                }
                case 3 -> {
                    columnName = "Measurements";
                    System.out.println("Enter new measurements and type 'done' when finished:");
                    newValue = buildString().toString();
                }
                case 4 -> {
                    columnName = "Ingredients";
                    System.out.println("Enter new ingredients and type 'done' when finished:");
                    newValue = buildString().toString();
                }
                case 5 -> {
                    columnName = "Instructions";
                    System.out.println("Enter new instructions and type 'done' when finished:");
                    newValue = buildString().toString();
                }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }
            updateRecipeField(columnName, newValue, recipeName);
            if (choice == 1) {
                recipeName = newValue;
            }
        }
    }

    /**
     * Updates a specific field of a recipe in the RECIPE table
     * 
     * @param columnName The column in the RECIPE table to update
     * @param newValue The new value that will replace the existing
     * @param recipeName The name of the recipe the user wants to change
     */
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

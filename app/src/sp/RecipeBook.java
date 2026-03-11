package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RecipeBook {

    private final Connection connection;
    private final Scanner scanner;

    public RecipeBook(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run() {
        System.out.println("\n+++++ Recipe Book +++++");
        System.out.println("1. View All Recipes");
        System.out.println("2. View Recipe Card");
        System.out.println("3. Add New Recipe");
        System.out.println("4. Exit to Main Menu");


        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 4) {
            System.out.println("Exiting Recipe Book...");
            return;
        }
        browseRecipes(choice);
    }

    public void browseRecipes(int choice) {
            switch (choice) {
                case 1 -> {
                    viewAll();
                    break;
                }
                case 2 -> {
                    viewCard();
                    break;
                }
                case 3 -> {
                    addRecipe();
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
     * Displays recipe card of selected recipe
     */
    public void viewCard(){
        PreparedStatement ps = null;
        ResultSet rs = null; 

        String recipe; 
        System.out.println("\nEnter name of recipe: ");
        recipe = scanner.nextLine();

        try{
        ps = connection.prepareStatement("""
            SELECT Meal_name, Measurements, Ingredients, Instructions
            FROM RECIPE
            WHERE Meal_name = ?
            """);

        ps.setString(1, recipe);
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
           // System.out.println("Ingredients: \n" + rs.getString("Measurements") + " " + rs.getString("Ingredients"));
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
    }

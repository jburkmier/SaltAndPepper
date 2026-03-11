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
<<<<<<< HEAD
        System.out.println("3. Add New Recipe");
        System.out.println("4. Exit");
=======
        System.out.println("3. Exit");
>>>>>>> origin/main

        int choice = scanner.nextInt();
        scanner.nextLine();

<<<<<<< HEAD
        if (choice == 4) {
=======
        if (choice == 3) {
>>>>>>> origin/main
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
<<<<<<< HEAD
                }
=======
                    }
>>>>>>> origin/main
                case 2 -> {
                    viewCard();
                    break;
                }
<<<<<<< HEAD
                case 3 -> {
                    addRecipe();
                }
=======
>>>>>>> origin/main
                default -> System.out.println("Invalid choice");
            }
        }
        

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

    public void viewCard(){
        PreparedStatement ps = null;
        ResultSet rs = null; 

        String recipe; 
<<<<<<< HEAD
        System.out.println("\nEnter name of recipe: ");
=======
        System.out.println("\n Enter name of recipe: ");
>>>>>>> origin/main
        recipe = scanner.nextLine();

        try{
        ps = connection.prepareStatement("""
<<<<<<< HEAD
            SELECT Meal_name, Measurements, Ingredients, Instructions
=======
            SELECT Meal_name, Ingredients, Instructions
>>>>>>> origin/main
            FROM RECIPE
            WHERE Meal_name = ?
            """);

        ps.setString(1, recipe);
        rs = ps.executeQuery();

        if (rs.next()){
<<<<<<< HEAD
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
=======
            System.out.println("\n" + rs.getString("Meal_name"));
            System.out.println("Ingredients: \n" + rs.getString("Ingredients"));
            System.out.println("Instructions: \n" + rs.getString("Instructions"));
>>>>>>> origin/main
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
<<<<<<< HEAD

    public void addRecipe(){
        PreparedStatement ps = null;
        ResultSet rs = null; 

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

=======
>>>>>>> origin/main
}

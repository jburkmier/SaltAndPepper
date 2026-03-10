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
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 3) {
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
        System.out.println("\n Enter name of recipe: ");
        recipe = scanner.nextLine();

        try{
        ps = connection.prepareStatement("""
            SELECT Meal_name, Ingredients, Instructions
            FROM RECIPE
            WHERE Meal_name = ?
            """);

        ps.setString(1, recipe);
        rs = ps.executeQuery();

        if (rs.next()){
            System.out.println("\n" + rs.getString("Meal_name"));
            System.out.println("Ingredients: \n" + rs.getString("Ingredients"));
            System.out.println("Instructions: \n" + rs.getString("Instructions"));
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
}

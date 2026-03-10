package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.HashMap;

public class MealPlan {
    private final Connection connection;
    private final Scanner scanner;
    private HashMap<String, HashMap<String, String>> mealPlan = new HashMap<>();


    public MealPlan(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run() {
        System.out.println("\n+++++ Meal Plan +++++");
        System.out.println("1. View Weekly Meal Plan");
        System.out.println("2. Create Meal Plan");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 3) {
            System.out.println("Exiting Meal Plan...");
            return;
        }
        handleMealPlan(choice);
    }

    public void handleMealPlan(int choice) {
        switch (choice) {
            case 1 -> {
                initializeMealPlan();
                viewMealPlan();
                break;
                }
            case 2 -> {
                break;
            }
            default -> System.out.println("Invalid choice");
            }
        }
    
    public void handleMealPlan(){
        System.out.println("Weekly Meal Plan"); 
    }

    public void viewMealPlan(){
        System.out.println("\n+++++ Meal Plan +++++");

        System.out.printf("%-10s %-15s %-15s %-15s\n",
            "Day", "Breakfast", "Lunch", "Dinner");

        for (String day : mealPlan.keySet()){
            HashMap<String, String> meals = mealPlan.get(day);

        System.out.printf("%-10s %-15s %-15s %-15s\n",
                day,
                meals.getOrDefault("Breakfast", "-"),
                meals.getOrDefault("Lunch", "-"),
                meals.getOrDefault("Dinner", "-"));        
            }
        }


    public void initializeMealPlan(){
        HashMap<String, String> monday = new HashMap<>();
        monday.put("Breakfast", "Oatmeal");
        monday.put("Lunch", "Salad");
        monday.put("Dinner", "Roast Chicken");

        mealPlan.put("Monday", monday);
    }

}

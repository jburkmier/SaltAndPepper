package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Browse {

    private final Connection connection;
    private final Scanner scanner;

    public Browse(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run() {
        System.out.println("+++++ Browse Recipes +++++");
        System.out.println("1. View all recipes");
        System.out.println("2. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 2) {
            System.out.println("Exiting Browse...");
            return;
        }

        browseRecipes(choice);
    }

    public void browseRecipes(int choice) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            switch (choice) {
                case 1 -> {
                    System.out.println("\nRecipes: ");
                    ps = connection.prepareStatement("""
                        SELECT Recipe.Meal_name
                        FROM RECIPE
                    """);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        System.out.println(rs.getString("Meal_name"));
                    }
                }
                default -> System.out.println("Invalid choice");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while browsing recipes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
}

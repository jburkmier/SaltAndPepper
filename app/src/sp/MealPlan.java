package sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MealPlan {
    private final Connection connection;
    private final Scanner scanner;

    public MealPlan(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run() {
        System.out.println("\n+++++ Meal Plan +++++");
        System.out.println("1. View Weekly Meal Plan");
    }
}

package sp;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.Console;
import java.sql.Connection;
import java.util.Scanner;

public class UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static Connection connection;
    
    public void run(){
        System.out.println("====== Welcome to Salt and Pepper! ======");
        connectOrExit();
        mainMenu();
        closeConnection();
    }

    public void connectOrExit(){
        while (true) { 
            System.out.println("\nSalt and Pepper SQL Connection");

            String url = promptRequiredString(scanner, "URL (ex: jdbc:mysql://localhost:3306/SaltAndPepper)");
            String user = promptRequiredString(scanner, "Database Username");
            String pass = promptPassword("Database Password");

			try {
				connection = DriverManager.getConnection(url, user, pass);
				System.out.println("Connected successfully.");
				return;
			} catch (SQLException ex) {
				System.out.println("Connection failed: " + ex.getMessage());
				boolean retry = confirm(scanner, "Try again?");
				if (!retry) {
					System.out.println("Exiting.");
					System.exit(0);
				}
		    }
        }
    }
    
    public void mainMenu() {
        boolean running = true; 
        while (running) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch(choice){
                case 1 -> {
                    new RecipeBook(scanner, connection).run();
                }
                case 2 -> {
                    running = false; 
                }
            }


        }
    }

    private void printMainMenu() {
        System.out.println("\n+++++ Main Menu +++++ ");
        System.out.println("1. Open Recipe Book");
        System.out.println("2. Exit");
    }

	// Required string prompt
	public static String promptRequiredString(Scanner scanner, String label) {
		while (true) {
			System.out.print(label + ": ");
			String value = scanner.nextLine().trim();
			if (!value.isEmpty()) return value;
			System.out.println("This field is required.");
		}
	}
    
    //Makes password appear as blank when typed in command line
	private String promptPassword(String prompt) {
		Console console = System.console();
		if (console != null) {
			char[] passwordArray = console.readPassword(prompt + ": ");
			return new String(passwordArray);
		} else {
			System.out.print(prompt + ": ");
			return scanner.nextLine();
		}
	}

    //confirm yes or no message
	public static boolean confirm(Scanner scanner, String message) {
		while (true) {
			System.out.print(message + " (y/n): ");
			String raw = scanner.nextLine().trim().toLowerCase();
			if (raw.equals("y")) return true;
			if (raw.equals("n")) return false;
			System.out.println("Please type 'y' or 'n'.");
		}
	}

	//close the connection
	private void closeConnection() {
		if (connection == null) return;
		try {
			connection.close();
		} catch (SQLException ignored) {
		}
	}
}

package sp;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Browse {
    private final Scanner scanner; 
    private final Connection connection;

    public Browse(Scanner scanner, Connection connection){
        this.scanner = scanner;
        this.connection = connection;
    }

    public void run(){
        System.out.println("\nWelcome to the Browse Section");
    }
}

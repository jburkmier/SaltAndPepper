package sp.JavaFXGUI;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import javafx.scene.control.ListView;

import sp.CookBook;


public class SaltAndPepperApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //Label title = new Label("Welcome to Salt and Pepper!");

        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/saltandpepper",
            "root",
            "Lachele58*"
        );

        CookBook cookBook = new CookBook(connection);

        Button viewAllBtn = new Button("View All Recipes");
        Button searchRecipesBtn = new Button("Search Recipe By Name");
        Button addNewRecipeBtn = new Button("Add New Recipe");

        Tab cookBookTab = new Tab("Cook Book");
        cookBookTab.setClosable(false);
        cookBookTab.setContent(new VBox(10, viewAllBtn, searchRecipesBtn, addNewRecipeBtn));

        viewAllBtn.setOnAction(event -> {
            List<String> recipes = cookBook.viewAll();
            ListView<String> recipeList = new ListView<>();
            recipeList.getItems().addAll(recipes);
            cookBookTab.setContent(recipeList);
        });

        Button viewMealPlanBtn = new Button("View Meal Plan");

        Tab mealPlanTab = new Tab("Meal Plan");
        mealPlanTab.setClosable(false);
        mealPlanTab.setContent(new VBox(10, viewMealPlanBtn));

        TabPane tabPane = new TabPane(cookBookTab, mealPlanTab);

        BorderPane pane = new BorderPane();
        //pane.setTop(title);
        pane.setCenter(tabPane);

        Scene scene = new Scene(pane, 400, 300);

        stage.setTitle("Salt and Pepper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


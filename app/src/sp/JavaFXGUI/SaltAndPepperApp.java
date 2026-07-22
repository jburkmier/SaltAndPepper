package sp.JavaFXGUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import sp.CookBook;
import sp.Recipe;

public class SaltAndPepperApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/saltandpepper",
                "root",
                "Lachele58*"
        );

        CookBook cookBook = new CookBook(connection);

        Button viewAllBtn = new Button("View All Recipes");
        Button searchRecipesBtn = new Button("Search Recipes");
        Button addNewRecipeBtn = new Button("Add Recipe");

        HBox buttonBar = new HBox(10);
        buttonBar.getChildren().addAll(viewAllBtn, searchRecipesBtn, addNewRecipeBtn);

        Label recipeName = new Label("Select a recipe");
        Label category = new Label();
        Label measurementsTitle = new Label("Measurements");
        Label measurements = new Label();
        Label ingredientsTitle = new Label("Ingredients");
        Label ingredients = new Label();
        Label instructionsTitle = new Label("Instructions");
        Label instructions = new Label();

        measurements.setWrapText(true);
        ingredients.setWrapText(true);
        instructions.setWrapText(true);

        VBox recipeCard = new VBox(
                10,
                recipeName,
                category,
                measurementsTitle,
                measurements,
                ingredientsTitle,
                ingredients,
                instructionsTitle,
                instructions
        );
        recipeCard.setPrefWidth(350);
        recipeCard.setStyle("""
                -fx-padding: 20;
                -fx-border-color: lightgray;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-background-color: white;
                """);
        recipeCard.setVisible(false);
        recipeCard.setManaged(false);

        Accordion categoryAccordion = new Accordion();
        SplitPane recipeArea = new SplitPane(categoryAccordion, recipeCard);
        recipeArea.setDividerPositions(0.40);

        VBox cookBookLayout = new VBox(10, buttonBar, recipeArea);

        Tab cookbookTab = new Tab("Cook Book");
        cookbookTab.setClosable(false);
        cookbookTab.setContent(cookBookLayout);

        Button mealPlanBtn = new Button("View Meal Plan");
        VBox mealLayout = new VBox(10, mealPlanBtn);

        Tab mealTab = new Tab("Meal Plan");
        mealTab.setClosable(false);
        mealTab.setContent(mealLayout);

        TabPane tabPane = new TabPane(cookbookTab, mealTab);

        viewAllBtn.setOnAction(event -> {
            List<Recipe> recipes = cookBook.viewAll();
            categoryAccordion.getPanes().clear();
            Map<String, List<Recipe>> recipesByCategory = new LinkedHashMap<>();

            for (Recipe recipe : recipes) {
                String categoryName = recipe.getCategory();
                if (categoryName == null || categoryName.isBlank()) {
                    categoryName = "Miscellaneous";
                }
                recipesByCategory.computeIfAbsent(categoryName, key -> new ArrayList<>()).add(recipe);
            }

            for (Map.Entry<String, List<Recipe>> entry : recipesByCategory.entrySet()) {
                String categoryName = entry.getKey();
                List<Recipe> categoryRecipes = entry.getValue();

                ListView<Recipe> categoryRecipeList = new ListView<>();
                categoryRecipeList.getItems().addAll(categoryRecipes);
                categoryRecipeList.setPrefHeight(Math.min(categoryRecipes.size() * 32 + 10, 220));

                categoryRecipeList.getSelectionModel().selectedItemProperty().addListener((obs, oldRecipe, selectedRecipe) -> {
                    if (selectedRecipe == null) {
                        return;
                    }
                    showRecipeCard(selectedRecipe, recipeCard, recipeName, category, measurements, ingredients, instructions);
                });

                TitledPane categoryPane = new TitledPane(categoryName, categoryRecipeList);
                categoryPane.setExpanded(false);
                categoryAccordion.getPanes().add(categoryPane);
            }

            recipeCard.setVisible(false);
            recipeCard.setManaged(false);
        });

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Salt and Pepper");
        stage.setScene(scene);
        stage.show();
    }

    private void showRecipeCard(
            Recipe recipe,
            VBox recipeCard,
            Label recipeName,
            Label category,
            Label measurements,
            Label ingredients,
            Label instructions
    ) {
        recipeCard.setVisible(true);
        recipeCard.setManaged(true);

        recipeName.setText(recipe.getMealName());

        String categoryValue = recipe.getCategory();
        category.setText(categoryValue == null || categoryValue.isBlank()
                ? "Category: Uncategorized"
                : "Category: " + categoryValue);

        String measurementValue = recipe.getMeasurements();
        measurements.setText(measurementValue == null || measurementValue.isBlank()
                ? "No measurements listed"
                : measurementValue);

        String ingredientValue = recipe.getIngredients();
        ingredients.setText(ingredientValue == null || ingredientValue.isBlank()
                ? "No ingredients listed"
                : ingredientValue);

        String instructionValue = recipe.getInstructions();
        instructions.setText(instructionValue == null || instructionValue.isBlank()
                ? "No instructions listed"
                : instructionValue);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

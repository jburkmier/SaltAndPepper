package sp.JavaFXGUI;

import javafx.fxml.FXML;

public class MainController {
    
     @FXML
    private void viewRecipes() {
        System.out.println("View Recipes clicked");
    }

    @FXML
    private void addRecipe() {
        System.out.println("Add Recipe clicked");
    }

    @FXML
    private void viewMealPlan() {
        System.out.println("Meal Planner clicked");
    }
}

package sp;

public class Recipe {

    private final String mealName;
    private final String category;
    private final String measurements;
    private final String ingredients;
    private final String instructions;

    public Recipe(
            String mealName,
            String category,
            String measurements,
            String ingredients,
            String instructions
    ) {
        this.mealName = mealName;
        this.category = category;
        this.measurements = measurements;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getMealName() {
        return mealName;
    }

    public String getCategory() {
        return category;
    }

    public String getMeasurements(){
        return measurements;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        String displayedCategory =
                category == null ? "Uncategorized" : category;

        return displayedCategory + ": " + mealName;
    }

}

package sp;

import java.util.HashMap; 

public class MealPlanDay {
    String breakfast = ""; 
    String lunch = ""; 
    String dinner = ""; 

    HashMap<String, HashMap<String, String>> mealPlan = new HashMap<>();

    public MealPlanDay(String breakfast, String lunch, String dinner){
        this.breakfast = breakfast; 
        this.lunch = lunch; 
        this.dinner = dinner;
    }


}

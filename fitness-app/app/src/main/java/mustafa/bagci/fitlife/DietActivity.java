package mustafa.bagci.fitlife;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mustafa.bagci.fitlife.diet.DietItem;
import mustafa.bagci.fitlife.diet.DietListAdapter;

/**
 * Activity for selecting diets.
 */
public class DietActivity extends AppCompatActivity {

    private static DietSelectionListener dietSelectionListener;
    private List<DietItem> dietItems;
    private DietListAdapter dietAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        // Enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView and diet items
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDiet);
        dietItems = generateDietItems();

        // Initialize and set up the DietListAdapter
        dietAdapter = new DietListAdapter(this, dietItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dietAdapter);
    }

    @Override
    public void onBackPressed() {
        // Get selected diets when the back button is pressed
        List<DietItem> selectedDiets = dietAdapter.getSelectedDiets();

        // Display a toast message based on whether diets are chosen or not
        if (selectedDiets.isEmpty()) {
            showToast("No diets chosen!");
        } else {
            // Calculate total calories of selected diets
            int totalCalories = calculateTotalCalories(selectedDiets);

            // Notify the MainActivity about the selected diets
            if (dietSelectionListener != null) {
                dietSelectionListener.onDietSelected(selectedDiets);
            }

            // Update the progress bar in the MainActivity
            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.updateProgressBar(totalCalories);

            // Display a toast message indicating that diets are chosen successfully
            showToast("Diets chosen successfully!");
        }

        // Finish the current activity
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Generates a list of predefined diet items.
     *
     * @return List of diet items.
     */
    private List<DietItem> generateDietItems() {
        // Populate the list with predefined diet items
        List<DietItem> items = new ArrayList<>();
        items.add(new DietItem("Morning (800 Calories)", "Oatmeal with milk, walnuts", 800));
        items.add(new DietItem("Lunch (1000 Calories)", "Chicken salad, brown rice", 1000));
        items.add(new DietItem("Dinner (1200 Calories)", "Steamed vegetables, salmon",1200));

        items.add(new DietItem("Morning (700 Calories)", "Fruit smoothie, almonds", 700));
        items.add(new DietItem("Lunch (900 Calories)", "Grilled chicken, whole wheat bulgur", 900));
        items.add(new DietItem("Dinner (1100 Calories)", "Quinoa salad, baked fish", 1100));

        items.add(new DietItem("Morning (600 Calories)", "Eggs, whole wheat bread", 600));
        items.add(new DietItem("Lunch (800 Calories)", "Bulgur pilaf, red lentil soup", 800));
        items.add(new DietItem("Dinner (1000 Calories)", "Grilled vegetables, turkey", 1000));

        items.add(new DietItem("Morning (500 Calories)", "Yogurt, banana, granola", 500));
        items.add(new DietItem("Lunch (700 Calories)", "Chicken salad, brown pasta", 700));
        items.add(new DietItem("Dinner (900 Calories)", "Steamed broccoli, grilled salmon", 900));

        items.add(new DietItem("Morning (900 Calories)", "Protein shake, hazelnuts", 900));
        items.add(new DietItem("Lunch (1100 Calories)", "Steamed chicken, quinoa", 1100));
        items.add(new DietItem("Dinner (1300 Calories)", "Stir-fried tofu, mixed vegetables", 1300));

        return items;
    }

    /**
     * Calculates the total calories of selected diet items.
     *
     * @param dietItems List of selected diet items.
     * @return Total calories of selected diet items.
     */
    private int calculateTotalCalories(List<DietItem> dietItems) {
        int totalCalories = 0;
        for (DietItem dietItem : dietItems) {
            totalCalories -= dietItem.getCalories();
        }
        return totalCalories;
    }

    /**
     * Interface for communicating diet selections to other components.
     */
    public interface DietSelectionListener {
        void onDietSelected(List<DietItem> selectedDiets);
    }

    /**
     * Displays a short toast message.
     *
     * @param message The message to be displayed.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

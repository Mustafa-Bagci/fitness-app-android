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

import mustafa.bagci.fitlife.exercise.ExerciseItem;
import mustafa.bagci.fitlife.exercise.ExerciseListAdapter;

/**
 * Activity for selecting exercises.
 */
public class ExercisesActivity extends AppCompatActivity {

    private static ExerciseSelectionListener exerciseSelectionListener;
    private List<ExerciseItem> exerciseItems;
    private ExerciseListAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView and exercise items
        RecyclerView recyclerView = findViewById(R.id.recyclerViewExercise);
        exerciseItems = generateExerciseItems();

        // Initialize and set up the ExerciseListAdapter
        exerciseAdapter = new ExerciseListAdapter(this, exerciseItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exerciseAdapter);
    }

    @Override
    public void onBackPressed() {
        // Get selected exercises when the back button is pressed
        List<ExerciseItem> selectedExercises = exerciseAdapter.getSelectedExercises();

        // Display a toast message based on whether exercises are chosen or not
        if (selectedExercises.isEmpty()) {
            showToast("No exercises chosen!");
        } else {
            // Calculate total calories of selected exercises
            int totalCalories = calculateTotalCalories(selectedExercises);

            // Notify the MainActivity about the selected exercises
            if (exerciseSelectionListener != null) {
                exerciseSelectionListener.onExerciseSelected(selectedExercises);
            }

            // Update the progress bar in the MainActivity
            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.updateProgressBar(totalCalories);

            // Display a toast message indicating that exercises are chosen successfully
            showToast("Exercises chosen successfully!");
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
     * Generates a list of predefined exercise items.
     *
     * @return List of exercise items.
     */
    private List<ExerciseItem> generateExerciseItems() {
        // Populate the list with predefined exercise items
        List<ExerciseItem> items = new ArrayList<>();
        items.add(new ExerciseItem("Jumping Jacks (5 minutes)", "Calories: 100", R.drawable.exercise1, 100));
        items.add(new ExerciseItem("Running in Place (7 minutes)", "Calories: 120", R.drawable.exercise2, 120));
        items.add(new ExerciseItem("Push-ups (3 minutes)", "Calories: 80", R.drawable.exercise3, 80));
        items.add(new ExerciseItem("Sit-ups (4 minutes)", "Calories: 90", R.drawable.exercise4, 90));
        items.add(new ExerciseItem("Squats (6 minutes)", "Calories: 110", R.drawable.exercise5, 110));
        items.add(new ExerciseItem("Lunges (5 minutes)", "Calories: 100", R.drawable.exercise6, 100));
        items.add(new ExerciseItem("Plank (2 minutes)", "Calories: 70", R.drawable.exercise7, 70));
        items.add(new ExerciseItem("Burpees (8 minutes)", "Calories: 130", R.drawable.exercise8, 130));
        return items;
    }

    /**
     * Calculates the total calories of selected exercise items.
     *
     * @param exerciseItems List of selected exercise items.
     * @return Total calories of selected exercise items.
     */
    private int calculateTotalCalories(List<ExerciseItem> exerciseItems) {
        int totalCalories = 0;
        for (ExerciseItem exerciseItem : exerciseItems) {
            totalCalories += exerciseItem.getCalories();
        }
        return totalCalories;
    }

    /**
     * Interface for communicating exercise selections to other components.
     */
    public interface ExerciseSelectionListener {
        void onExerciseSelected(List<ExerciseItem> selectedExercises);
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

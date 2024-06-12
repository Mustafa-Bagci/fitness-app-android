package mustafa.bagci.fitlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import mustafa.bagci.fitlife.diet.DietItem;
import mustafa.bagci.fitlife.exercise.ExerciseItem;

public class MainActivity extends AppCompatActivity
        implements ExercisesActivity.ExerciseSelectionListener, DietActivity.DietSelectionListener {

    // Constants for SharedPreferences keys and request codes
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_CALORIE_GOAL = "calorie_goal";
    private static final String KEY_CURRENT_CALORIES = "current_calories";
    private static final int REQUEST_EXERCISES = 0;
    private static final int REQUEST_DIET = 0;

    // Singleton instance of MainActivity
    private static MainActivity instance;

    // UI components
    private ProgressBar progressBar;
    private int currentCalories;
    private int goalCalories;

    /**
     * Initializes the main screen of the application.
     *
     * @param savedInstanceState Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        CardView calorie = findViewById(R.id.calorie);
        CardView exercises = findViewById(R.id.exercises);
        CardView diet = findViewById(R.id.diet);

        progressBar = findViewById(R.id.calorieProgressBar);
        TextView calorieGoalTextView = findViewById(R.id.calorieGoalTextView);

        String savedCalorieGoal = getSavedCalorieGoal();
        goalCalories = Integer.parseInt(savedCalorieGoal);
        currentCalories = 0;

        progressBar.setMax(goalCalories);
        progressBar.setProgress(currentCalories);

        instance = this;

        String currentCaloriesText = "Your Calorie Goal: " + goalCalories + " | Current Calories: " + currentCalories;

        calorieGoalTextView.setText(currentCaloriesText);

        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalorieActivity.class);
                startActivity(intent);
            }
        });

        exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExercisesActivity.class);
                startActivityForResult(intent, REQUEST_EXERCISES);
            }
        });

        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DietActivity.class);
                startActivityForResult(intent, REQUEST_DIET);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Retrieves the saved calorie goal from SharedPreferences.
     *
     * @return The saved calorie goal as a String.
     */
    private String getSavedCalorieGoal() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CALORIE_GOAL, "0");
    }

    /**
     * Retrieves the saved current calories from SharedPreferences.
     *
     * @return The saved current calories as an integer.
     */
    private int getSavedCurrentCalories() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_CURRENT_CALORIES, 0);
    }

    /**
     * Saves the current calories to SharedPreferences.
     *
     * @param currentCalories The current calories to be saved.
     */
    private void saveCurrentCalories(int currentCalories) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CURRENT_CALORIES, currentCalories);
        editor.apply();
    }

    /**
     * Creates the options menu in the app bar.
     *
     * @param menu The options menu.
     * @return True if the menu is successfully created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    /**
     * Handles options menu item selection.
     *
     * @param item The selected menu item.
     * @return True if the menu item is successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetValues();
            return true;
        }

        if (id == R.id.action_exit) {
            showExitConfirmationDialog();
            return true;
        }

        if (id == R.id.action_finish) {
            showRecommendationDialog(currentCalories);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Resets the values to default after user confirmation.
     */
    private void resetValues() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Reset Values");
        builder.setMessage("Are you sure you want to reset to default values ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetValuesInternal();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Resets the values internally.
     */
    private void resetValuesInternal() {
        goalCalories = 0;
        currentCalories = 0;
        progressBar.setProgress(0);

        TextView calorieGoalTextView = findViewById(R.id.calorieGoalTextView);
        calorieGoalTextView.setText("Your Calorie Goal: " + goalCalories + " | Current Calories: " + currentCalories);

        clearSharedPreferences();
    }

    /**
     * Clears the SharedPreferences.
     */
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Handles the selection of exercises and updates the progress bar.
     *
     * @param selectedExercises List of selected exercises.
     */
    @Override
    public void onExerciseSelected(List<ExerciseItem> selectedExercises) {
        int totalCalories = 0;
        updateProgressBar(totalCalories);
    }

    /**
     * Handles the selection of diet plans and updates the progress bar.
     *
     * @param selectedDiets List of selected diet plans.
     */
    @Override
    public void onDietSelected(List<DietItem> selectedDiets) {
        int totalCalories = 0;
        updateProgressBar(totalCalories);
    }

    /**
     * Updates the progress bar and UI with consumed calories information.
     *
     * @param consumedCalories The consumed calories to be added to the total.
     */
    public void updateProgressBar(int consumedCalories) {
        this.currentCalories += consumedCalories;

        int currentCalories = -this.currentCalories;

        progressBar.setMax(goalCalories);
        progressBar.setProgress(currentCalories);

        TextView calorieGoalTextView = findViewById(R.id.calorieGoalTextView);

        String currentCaloriesText = "Your Calorie Goal: " + goalCalories + " | Current Calories: " + (currentCalories >= 0 ? currentCalories : "-" + Math.abs(currentCalories));

        calorieGoalTextView.setText(currentCaloriesText);

        saveCurrentCalories(currentCalories);

        if (currentCalories == goalCalories) {
            showCongratulationsDialog();
        }
    }

    /**
     * Returns the singleton instance of the MainActivity class.
     *
     * @return The singleton instance of MainActivity.
     */
    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    /**
     * Shows the congratulations dialog when the calorie goal is reached.
     */
    private void showCongratulationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Congratulations!");
        builder.setMessage("You have reached your calorie goal.");

        final MediaPlayer applauseSound = MediaPlayer.create(this, R.raw.applause_sound);
        applauseSound.start();
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applauseSound.release();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Displays a toast message with the given message.
     *
     * @param message The message to be displayed in the toast.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows the exit confirmation dialog before exiting the app.
     */
    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit the app?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Shows the daily recommendation dialog based on calorie goal achievement.
     *
     * @param caloriesDifference The difference between goal calories and current calories.
     */
    private void showRecommendationDialog(final int caloriesDifference) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Daily Recommendation");

        if (goalCalories < getSavedCurrentCalories()) {
            builder.setMessage("Your calories are above your goal! Consider doing 10 minutes of aerobics to balance it out.");
        } else {
            builder.setMessage("Your calories are under your goal! Consider enjoying a small chocolate for a treat.");
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentCalories -= goalCalories;
                handleRecommendationAcceptance(caloriesDifference);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Handles the acceptance of the recommendation and updates the progress bar accordingly.
     *
     * @param caloriesDifference The difference between goal calories and current calories.
     */
    private void handleRecommendationAcceptance(int caloriesDifference) {

        if (goalCalories > currentCalories) {
            currentCalories -= caloriesDifference;
        } else if (goalCalories < currentCalories) {
            currentCalories += caloriesDifference;
        }
        updateProgressBar(0);
    }
}
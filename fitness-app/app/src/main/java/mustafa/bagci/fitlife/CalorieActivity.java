package mustafa.bagci.fitlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for setting and saving the daily calorie goal.
 */
public class CalorieActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";        // SharedPreferences name
    private static final String KEY_CALORIE_GOAL = "calorie_goal";  // Key for storing the calorie goal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        final EditText calorieInput = findViewById(R.id.calorieInput);
        Button okButton = findViewById(R.id.okButton);

        // Set click listener for the "OK" button
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered calorie value from the EditText
                String calorieValue = calorieInput.getText().toString();

                // Check if the input is empty
                if (TextUtils.isEmpty(calorieValue)) {
                    // Display a toast message prompting the user to enter a valid calorie value
                    Toast.makeText(CalorieActivity.this, R.string.enter_valid_calorie, Toast.LENGTH_SHORT).show();
                } else {
                    // Save the entered calorie value to SharedPreferences
                    saveCalorieGoal(calorieValue);

                    // Display a toast message indicating that the calorie goal is saved
                    Toast.makeText(CalorieActivity.this, R.string.calorie_goal_saved, Toast.LENGTH_SHORT).show();

                    // Navigate to the MainActivity
                    Intent intent = new Intent(CalorieActivity.this, MainActivity.class);
                    startActivity(intent);

                    // Finish the current activity
                    finish();
                }
            }
        });
    }

    /**
     * Saves the entered calorie goal to SharedPreferences.
     *
     * @param calorieValue The entered calorie value to be saved.
     */
    private void saveCalorieGoal(String calorieValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CALORIE_GOAL, calorieValue);
        editor.apply();
    }
}

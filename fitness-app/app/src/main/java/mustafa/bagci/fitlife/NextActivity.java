package mustafa.bagci.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // Find the "Next" button in the layout
        Button nextButton = findViewById(R.id.nextButton);

        // Set a click listener for the "Next" button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the MainActivity
                Intent intent = new Intent(NextActivity.this, MainActivity.class);

                // Start the MainActivity
                startActivity(intent);
            }
        });
    }
}

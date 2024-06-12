package mustafa.bagci.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the layout defined in activity_start.xml
        setContentView(R.layout.activity_start);

        // Find the "Start" button in the layout
        Button startButton = findViewById(R.id.startButton);

        // Set a click listener for the "Start" button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the NextActivity
                Intent intent = new Intent(StartActivity.this, NextActivity.class);

                // Start the NextActivity
                startActivity(intent);
            }
        });
    }
}

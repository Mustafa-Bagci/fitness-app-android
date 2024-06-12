package mustafa.bagci.fitlife.exercise;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mustafa.bagci.fitlife.R;

/**
 * Adapter class for the RecyclerView in the ExerciseListActivity.
 */
public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<ExerciseItem> exerciseItems;  // List of exercise items
    private LayoutInflater inflater;           // Inflater for creating views
    private static final String PREF_NAME = "MyPrefs";  // SharedPreferences name
    private static final String KEY_SELECTED_CALORIES = "selected_calories";  // Key for storing selected calories

    /**
     * Constructor for the ExerciseListAdapter.
     *
     * @param context       The context of the activity or fragment.
     * @param exerciseItems List of exercise items to display.
     */
    public ExerciseListAdapter(Context context, List<ExerciseItem> exerciseItems) {
        this.inflater = LayoutInflater.from(context);
        // Ensure exerciseItems is not null to prevent null pointer exceptions
        this.exerciseItems = new ArrayList<>(exerciseItems != null ? exerciseItems : new ArrayList<>());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for each item in the RecyclerView
        View view = inflater.inflate(R.layout.exercise_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseItem exerciseItem = exerciseItems.get(position);
        // Set the title, description, and image for the exercise item
        holder.textView.setText(exerciseItem.getTitle());
        holder.textDescription.setText(exerciseItem.getDescription());
        holder.imageViewExercise.setImageResource(exerciseItem.getImageResourceId());

        // Set text color based on whether the exercise item is selected or not
        int textColor = exerciseItem.isSelected() ?
                ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_text_color) :
                ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black);
        holder.textView.setTextColor(textColor);
        holder.textDescription.setTextColor(textColor);

        // Set click listener to toggle the selected state of the exercise item and save selected calories
        holder.itemView.setOnClickListener(v -> {
            exerciseItem.setSelected(!exerciseItem.isSelected());
            notifyItemChanged(position);
            saveSelectedCalories();
        });
    }

    @Override
    public int getItemCount() {
        // Returns the number of items in the RecyclerView
        return exerciseItems.size();
    }

    /**
     * Saves the total selected calories in SharedPreferences.
     */
    private void saveSelectedCalories() {
        int selectedCalories = calculateSelectedCalories();
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SELECTED_CALORIES, selectedCalories);
        editor.apply();
    }

    /**
     * Calculates the total calories of selected exercise items.
     *
     * @return Total selected calories.
     */
    public int calculateSelectedCalories() {
        int calories = 0;
        for (ExerciseItem exerciseItem : exerciseItems) {
            if (exerciseItem.isSelected()) {
                calories += exerciseItem.getCalories();
            }
        }
        return calories;
    }

    /**
     * Gets the list of selected exercise items.
     *
     * @return List of selected exercise items.
     */
    public List<ExerciseItem> getSelectedExercises() {
        List<ExerciseItem> selectedExercises = new ArrayList<>();
        for (ExerciseItem exerciseItem : exerciseItems) {
            if (exerciseItem.isSelected()) {
                selectedExercises.add(exerciseItem);
            }
        }
        return selectedExercises;
    }

    /**
     * ViewHolder class for caching View components of the item layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textDescription;
        ImageView imageViewExercise;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews and ImageView from the item layout
            textView = itemView.findViewById(R.id.textTitleExercise);
            textDescription = itemView.findViewById(R.id.textDescriptionExercise);
            imageViewExercise = itemView.findViewById(R.id.imageViewExercise);
        }
    }
}

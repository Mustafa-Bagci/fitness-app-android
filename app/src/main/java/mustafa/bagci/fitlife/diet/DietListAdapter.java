package mustafa.bagci.fitlife.diet;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mustafa.bagci.fitlife.R;

/**
 * Adapter class for the RecyclerView in the DietListActivity.
 */
public class DietListAdapter extends RecyclerView.Adapter<DietListAdapter.ViewHolder> {

    private List<DietItem> dietItems;  // List of diet items
    private LayoutInflater inflater;   // Inflater for creating views
    private static final String PREF_NAME = "MyPrefs";  // SharedPreferences name
    private static final String KEY_SELECTED_CALORIES = "selected_calories";  // Key for storing selected calories

    /**
     * Constructor for the DietListAdapter.
     *
     * @param context   The context of the activity or fragment.
     * @param dietItems List of diet items to display.
     */
    public DietListAdapter(Context context, List<DietItem> dietItems) {
        this.inflater = LayoutInflater.from(context);
        // Ensure dietItems is not null to prevent null pointer exceptions
        this.dietItems = new ArrayList<>(dietItems != null ? dietItems : new ArrayList<>());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for each item in the RecyclerView
        View view = inflater.inflate(R.layout.diet_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItem dietItem = dietItems.get(position);
        // Set the title and description of the diet item
        holder.textView.setText(dietItem.getTitle());
        holder.textDescription.setText(dietItem.getDescription());

        // Set text color based on whether the diet item is selected or not
        int textColor = dietItem.isSelected() ?
                ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_text_color) :
                ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black);
        holder.textView.setTextColor(textColor);
        holder.textDescription.setTextColor(textColor);

        // Set click listener to toggle the selected state of the diet item and save selected calories
        holder.itemView.setOnClickListener(v -> {
            dietItem.setSelected(!dietItem.isSelected());
            notifyItemChanged(position);
            saveSelectedCalories();
        });
    }

    @Override
    public int getItemCount() {
        // Returns the number of items in the RecyclerView
        return dietItems.size();
    }

    /**
     * Saves the total selected calories in SharedPreferences.
     */
    private void saveSelectedCalories() {
        int selectedCalories = calculateSelectedCalories(getSelectedDiets());
        SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SELECTED_CALORIES, selectedCalories);
        editor.apply();
    }

    /**
     * Calculates the total calories of selected diet items.
     *
     * @param dietItems List of selected diet items.
     * @return Total selected calories.
     */
    private int calculateSelectedCalories(List<DietItem> dietItems) {
        int totalCalories = 0;
        for (DietItem dietItem : dietItems) {
            totalCalories += dietItem.getCalories();
        }
        return totalCalories;
    }

    /**
     * Gets the list of selected diet items.
     *
     * @return List of selected diet items.
     */
    public List<DietItem> getSelectedDiets() {
        List<DietItem> selectedDiets = new ArrayList<>();
        for (DietItem dietItem : dietItems) {
            if (dietItem.isSelected()) {
                selectedDiets.add(dietItem);
            }
        }
        return selectedDiets;
    }

    /**
     * ViewHolder class for caching View components of the item layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textDescription;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews from the item layout
            textView = itemView.findViewById(R.id.textTitleDiet);
            textDescription = itemView.findViewById(R.id.textDescriptionDiet);
        }
    }
}

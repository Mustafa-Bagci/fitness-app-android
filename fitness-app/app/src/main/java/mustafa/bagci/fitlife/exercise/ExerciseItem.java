package mustafa.bagci.fitlife.exercise;

/**
 * Model class representing an exercise item.
 */
public class ExerciseItem {

    private String title;             // Title of the exercise item
    private String description;       // Description of the exercise item
    private int imageResourceId;      // Resource ID for the image associated with the exercise item
    private int calories;             // Caloric value of the exercise item
    private boolean selected;         // Flag indicating whether the exercise item is selected or not

    /**
     * Constructor for creating an ExerciseItem.
     *
     * @param title            Title of the exercise item.
     * @param description      Description of the exercise item.
     * @param imageResourceId  Resource ID for the image associated with the exercise item.
     * @param calories         Caloric value of the exercise item.
     */
    public ExerciseItem(String title, String description, int imageResourceId, int calories) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.calories = calories;
        this.selected = false;   // Default to not selected
    }

    /**
     * Gets the title of the exercise item.
     *
     * @return The title of the exercise item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the exercise item.
     *
     * @return The description of the exercise item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the resource ID for the image associated with the exercise item.
     *
     * @return The resource ID for the image.
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * Checks if the exercise item is selected.
     *
     * @return True if the exercise item is selected, false otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Gets the caloric value of the exercise item.
     *
     * @return The caloric value of the exercise item.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Sets the selected state of the exercise item.
     *
     * @param selected True to mark the exercise item as selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

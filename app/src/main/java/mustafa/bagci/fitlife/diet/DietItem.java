package mustafa.bagci.fitlife.diet;

/**
 * Model class representing a diet item.
 */
public class DietItem {
    private String title;        // Title of the diet item
    private String description;  // Description of the diet item
    private boolean selected;    // Flag indicating whether the diet item is selected or not
    private int calories;        // Caloric value of the diet item

    /**
     * Constructor for creating a DietItem.
     *
     * @param title       Title of the diet item.
     * @param description Description of the diet item.
     * @param calories    Caloric value of the diet item.
     */
    public DietItem(String title, String description, int calories) {
        this.title = title;
        this.description = description;
        this.selected = false;   // Default to not selected
        this.calories = calories;
    }

    /**
     * Gets the title of the diet item.
     *
     * @return The title of the diet item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the diet item.
     *
     * @return The description of the diet item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the diet item is selected.
     *
     * @return True if the diet item is selected, false otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state of the diet item.
     *
     * @param selected True to mark the diet item as selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Gets the caloric value of the diet item.
     *
     * @return The caloric value of the diet item.
     */
    public int getCalories() {
        return calories;
    }
}

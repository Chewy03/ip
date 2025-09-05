package task;

/**
 * Represents a simple task without a specific date or time.
 * <p>
 * A {@code ToDo} is a type of {@link Task} that only has a description
 * and completion status. It does not have a start or end date.
 * </p>
 */
public class ToDo extends Task {

    /**
     * Constructs a new {@code ToDo} with the given description.
     *
     * @param description the description of the todo task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this todo,
     * including its type, status, and description.
     *
     * @return a formatted string for display
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

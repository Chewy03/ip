package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * <p>
 * A {@code Deadline} is a type of {@link Task} that has an associated
 * due date and time. It can be displayed in a human-readable format
 * and inherits completion status handling from {@link Task}.
 * </p>
 */
public class Deadline extends Task {

    /** The due date and time for this deadline task. */
    protected LocalDateTime dueDate;

    /** Formatter for displaying the due date in a readable format. */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Constructs a new {@code Deadline} task with the given description
     * and due date.
     *
     * @param description the description of the deadline task
     * @param dueDate     the date and time by which the task is due
     */
    public Deadline(String description, LocalDateTime dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    /**
     * Returns the due date of this deadline task.
     *
     * @return the due date and time
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Returns a string representation of this deadline task,
     * including its type, status, description, and due date.
     *
     * @return a formatted string for display
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(DISPLAY_FORMAT) + ")";
    }
}

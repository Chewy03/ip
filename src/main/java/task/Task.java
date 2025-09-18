package task;

/**
 * The {@code Task} class represents a general task with a description and completion status.
 * <p>
 * A task can be marked as done or not done, and provides basic
 * functionality common to all task types (e.g., {@link task.ToDo},
 * {@link task.Deadline}, {@link task.Event}).
 * </p>
 */
public class Task {

    /** The textual description of the task. */
    protected String description;

    /** Whether the task has been marked as completed. */
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * By default, a new task is not marked as done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon used to represent whether the task is done.
     *
     * @return {@code "X"} if the task is done, otherwise a space {@code " "}
     */
    public String getMark() {
        return isDone ? "✔\uFE0F" : " ";
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return {@code true} if the task is done, {@code false} otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation of this task,
     * including its completion status and description.
     *
     * @return a formatted string representing the task
     */
    @Override
    public String toString() {
        return "[" + getMark() + "] " + description;
    }
}

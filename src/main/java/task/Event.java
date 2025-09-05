package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs during a specific time interval.
 * <p>
 * An {@code Event} is a type of {@link Task} that has both a start
 * and an end date/time. It inherits the completion status handling
 * from {@link Task}.
 * </p>
 */
public class Event extends Task {

    /** The start date and time of the event. */
    protected LocalDateTime start;

    /** The end date and time of the event. */
    protected LocalDateTime end;

    /** Formatter for displaying event dates in a readable format. */
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Constructs a new {@code Event} with the given description,
     * start time, and end time.
     *
     * @param description the description of the event
     * @param start       the start date and time of the event
     * @param end         the end date and time of the event
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start date and time of this event.
     *
     * @return the event start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns the end date and time of this event.
     *
     * @return the event end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns a string representation of this event,
     * including its type, status, description, start, and end time.
     *
     * @return a formatted string for display
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + start.format(DISPLAY_FORMAT)
                + " to: " + end.format(DISPLAY_FORMAT) + ")";
    }
}

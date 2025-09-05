package parser;

import error.JimmyTimmyException;
import task.Task;
import task.ToDo;
import task.Deadline;
import task.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The {@code Parser} class is responsible for interpreting and converting
 * raw user input into structured commands or {@link Task} objects.
 */
public class Parser {

    /** Date-time formatter used to parse deadlines and event times. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Parses a full command string into a specific {@link Task}.
     *
     * <p>Supported commands:</p>
     * <ul>
     *     <li>{@code todo description}</li>
     *     <li>{@code deadline description /by yyyy-MM-dd HHmm}</li>
     *     <li>{@code event description /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm}</li>
     * </ul>
     *
     * @param fullCommand the raw user command string
     * @return a {@link Task} object corresponding to the parsed command
     * @throws JimmyTimmyException if the command is invalid, incomplete, or has incorrect date/time format
     */
    public static Task parseTask(String fullCommand) throws JimmyTimmyException {
        String trimmed = fullCommand.trim();
        try {
            if (trimmed.startsWith("todo")) {
                String desc = trimmed.substring(4).trim();
                if (desc.isEmpty()) throw new JimmyTimmyException("The description of a todo cannot be empty.");
                return new ToDo(desc);

            } else if (trimmed.startsWith("deadline")) {
                String[] parts = trimmed.substring(8).split(" /by ", 2);
                if (parts.length < 2) throw new JimmyTimmyException("Deadline must have a description and a /by time.");
                String desc = parts[0].trim();
                LocalDateTime by = LocalDateTime.parse(parts[1].trim(), FORMATTER);
                return new Deadline(desc, by);

            } else if (trimmed.startsWith("event")) {
                String[] parts = trimmed.substring(5).split(" /from | /to ");
                if (parts.length < 3) throw new JimmyTimmyException("Event must have description, /from, and /to.");
                String desc = parts[0].trim();
                LocalDateTime start = LocalDateTime.parse(parts[1].trim(), FORMATTER);
                LocalDateTime end = LocalDateTime.parse(parts[2].trim(), FORMATTER);
                return new Event(desc, start, end);

            } else {
                throw new JimmyTimmyException("I don’t know what that means.");
            }
        } catch (DateTimeParseException e) {
            throw new JimmyTimmyException("Invalid date/time format. Use yyyy-MM-dd HHmm");
        }
    }

    /**
     * Extracts and parses a task index from a command string.
     * <p>Example: {@code "mark 2"} → returns {@code 1} (0-based index).</p>
     *
     * @param command the command string containing a task index
     * @return the parsed task index (0-based)
     * @throws JimmyTimmyException if the index is missing or not a valid integer
     */
    public static int parseIndex(String command) throws JimmyTimmyException {
        try {
            return Integer.parseInt(command.split(" ")[1].trim()) - 1;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new JimmyTimmyException("Please enter a valid task number.");
        }
    }

    /**
     * Determines if the given command is an exit command.
     *
     * @param command the user input
     * @return {@code true} if the command is "bye", ignoring case
     */
    public static boolean isExit(String command) {
        return command.equalsIgnoreCase("bye");
    }

    /**
     * Determines if the given command is a list command.
     *
     * @param command the user input
     * @return {@code true} if the command is "list", ignoring case
     */
    public static boolean isList(String command) {
        return command.equalsIgnoreCase("list");
    }
}

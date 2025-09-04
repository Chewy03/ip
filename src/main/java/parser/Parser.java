package parser;

import error.JimmyTimmyException;
import task.Task;
import task.ToDo;
import task.Deadline;
import task.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

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

    public static int parseIndex(String command) throws JimmyTimmyException {
        try {
            return Integer.parseInt(command.split(" ")[1].trim()) - 1;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new JimmyTimmyException("Please enter a valid task number.");
        }
    }

    public static boolean isExit(String command) {
        return command.equalsIgnoreCase("bye");
    }

    public static boolean isList(String command) {
        return command.equalsIgnoreCase("list");
    }
}
